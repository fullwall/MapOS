package com.fullwall.maps;

import java.util.Collections;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import com.fullwall.maps.applications.ApplicationFactory;
import com.fullwall.maps.applications.ApplicationProvider;
import com.fullwall.maps.applications.builtin.SettingsApp;
import com.fullwall.maps.os.ForwardingMapRenderer;
import com.fullwall.maps.os.OperatingSystem;
import com.google.common.collect.Maps;

public class MapController {
	public void addProvider(ApplicationFactory factory) {
		this.providers.put(factory.getName().toLowerCase(),
				new ApplicationProvider(factory));
	}

	public void deregisterSystem(String playerName) {
		OperatingSystem tmp = systems.remove(playerName);
		if (tmp != null)
			tmp.shutdown();
	}

	public ApplicationProvider getProvider(String key) {
		key = key.toLowerCase();
		return defaultProviders.containsKey(key) ? defaultProviders.get(key)
				: providers.get(key);
	}

	public OperatingSystem getSystem(Player player) {
		return systems.get(player.getName());
	}

	public boolean hasSystem(Player player) {
		return systems.containsKey(player.getName());
	}

	public void registerSystem(Player player, MapView map) {
		if (this.systems.containsKey(player.getName()))
			throw new IllegalArgumentException("player already has a system");
		update(map);
		systems.put(player.getName(), new OperatingSystem(this, player, map));
	}

	public void reset() {
		for (String player : systems.keySet())
			deregisterSystem(player);
		for (short id : registeredMapViews.keySet()) {
			MapView map = Bukkit.getMap(id);
			map.removeRenderer(map.getRenderers().get(0));
			map.addRenderer(registeredMapViews.get(id));
		}
	}

	public void update(MapView map) {
		if (!map.isVirtual() && map.getRenderers().size() == 1) {
			registeredMapViews.put(map.getId(), map.getRenderers().get(0));
			map.removeRenderer(map.getRenderers().get(0));
			map.addRenderer(new ForwardingMapRenderer(this));
		}
	}

	private final Map<String, OperatingSystem> systems = Maps.newHashMap();
	private final Map<String, ApplicationProvider> providers = Maps
			.newHashMap();
	private final Map<Short, MapRenderer> registeredMapViews = Maps
			.newHashMap();

	private static final Map<String, ApplicationProvider> defaultProviders = Maps
			.newLinkedHashMap();

	public static Map<String, ApplicationProvider> getDefaultProviders() {
		return Collections.unmodifiableMap(defaultProviders);
	}

	static {
		defaultProviders.put("settings", SettingsApp.Provider);
	}
}
