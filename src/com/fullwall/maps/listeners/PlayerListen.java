package com.fullwall.maps.listeners;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;

import com.fullwall.maps.MapController;
import com.fullwall.maps.MapOS;
import com.fullwall.maps.interfaces.ApplicationController.InterruptReason;
import com.fullwall.maps.interfaces.Screen;
import com.google.common.collect.Maps;

public class PlayerListen extends PlayerListener implements Listener {
	@Override
	public void onItemHeldChange(PlayerItemHeldEvent event) {
		ItemStack holding = event.getPlayer().getInventory()
				.getItem(event.getNewSlot());
		if (holding == null || holding.getType() == Material.AIR) {
			return;
		}
		boolean registered = controller.hasSystem(event.getPlayer()), map = holding
				.getType() == Material.MAP;
		MapView view = map ? Bukkit.getMap(holding.getDurability()) : null;
		if (map)
			controller.update(view);
		if (!registered && map) {
			controller.registerSystem(event.getPlayer(), view);
		} else if (registered && !map) {
			controller.getSystem(event.getPlayer()).getApplicationController()
					.interrupt(InterruptReason.TemporaryInterruption);
		} else if (map) {
			controller.getSystem(event.getPlayer()).getApplicationController()
					.resume();
		}
	}

	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		if (!controller.hasSystem(event.getPlayer()))
			return;
		controller.getSystem(event.getPlayer()).processChat(event);
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!controller.hasSystem(player)) {
			keyDelays.remove(player);
			return;
		}
		Screen screen = controller.getSystem(player).getScreen();
		MouseClick click;
		switch (event.getAction()) {
		case LEFT_CLICK_AIR:
		case LEFT_CLICK_BLOCK:
			click = MouseClick.Left;
			break;
		case RIGHT_CLICK_AIR:
		case RIGHT_CLICK_BLOCK:
			click = MouseClick.Right;
			break;
		default:
			return;
		}
		Boolean result = update(putIfAbsent(player), click);
		if (result == null)
			return;
		screen.forwardMouseEvent(click, result);
	}

	@Override
	public void onPlayerLogin(PlayerLoginEvent event) {
		registerPlayerIfAbsent(event.getPlayer());
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled() || !controller.hasSystem(event.getPlayer()))
			return;
		if (event.getTo().getBlockX() != event.getFrom().getBlockX()
				|| event.getFrom().getBlockY() != event.getTo().getBlockY()
				|| event.getFrom().getBlockZ() != event.getTo().getBlockZ()
				|| (event.getFrom().getYaw() == event.getTo().getYaw() && event
						.getFrom().getPitch() == event.getTo().getPitch())) {
			return;
		}
		int diffX = (int) Math.floor(event.getTo().getYaw()
				- event.getFrom().getYaw());
		int diffY = (int) Math.floor(event.getTo().getPitch()
				- event.getFrom().getPitch());
		controller.getSystem(event.getPlayer()).getScreen()
				.moveMouse(diffX / 2, diffY / 2);
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		keyDelays.remove(event.getPlayer());
		controller.deregisterSystem(event.getPlayer().getName());
	}

	@Override
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (!controller.hasSystem(event.getPlayer())) {
			keyDelays.remove(event.getPlayer());
			return;
		}
		controller.getSystem(event.getPlayer()).getScreen()
				.forwardKeyEvent(Key.Shift, event.isSneaking());
	}

	private Map<Enum<?>, Long> putIfAbsent(Player player) {
		if (keyDelays.containsKey(player)) {
			return keyDelays.get(player);
		}
		Map<Enum<?>, Long> map = Maps.newHashMap();
		keyDelays.put(player, map);
		return map;
	}

	private void registerPlayerIfAbsent(Player player) {
		ItemStack hand = player.getItemInHand();
		if (hand.getType() == Material.MAP && !controller.hasSystem(player)) {
			controller.registerSystem(player,
					Bukkit.getMap(hand.getDurability()));
		}
	}

	private Boolean update(Map<Enum<?>, Long> presses, Enum<?> update) {
		if (presses.containsKey(update)) {
			if (System.currentTimeMillis() - presses.get(update) > HoldDelay) {
				presses.remove(update);
				return false;
			} else
				presses.put(update, System.currentTimeMillis());
			return null;
		} else {
			presses.put(update, System.currentTimeMillis());
			return true;
		}
	}

	public enum Key {
		Shift;
	}

	public enum MouseClick {
		Left,
		Right;
	}

	private static final int HoldDelay = 500;

	private static final Map<Player, Map<Enum<?>, Long>> keyDelays = Maps
			.newHashMap();

	private MapController controller;

	@Override
	public void registerEvents(MapOS plugin, PluginManager pm) {
		this.controller = plugin.getController();
		pm.registerEvent(Event.Type.PLAYER_MOVE, this, Priority.Monitor, plugin);
		pm.registerEvent(Event.Type.PLAYER_ITEM_HELD, this, Priority.Monitor,
				plugin);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, this, Priority.Monitor,
				plugin);
		pm.registerEvent(Event.Type.PLAYER_QUIT, this, Priority.Monitor, plugin);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this, Priority.Monitor,
				plugin);
		pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, this,
				Priority.Monitor, plugin);
		pm.registerEvent(Event.Type.PLAYER_CHAT, this, Priority.Normal, plugin);
		for (Player player : Bukkit.getOnlinePlayers()) {
			registerPlayerIfAbsent(player);
		}
	}
}
