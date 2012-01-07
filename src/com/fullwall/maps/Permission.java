package com.fullwall.maps;

import org.bukkit.entity.Player;

public class Permission {
	public static boolean hasPermission(Player player, String permission) {
		return player.hasPermission(permission);
	}
}