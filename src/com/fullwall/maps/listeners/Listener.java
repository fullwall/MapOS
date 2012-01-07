package com.fullwall.maps.listeners;

import org.bukkit.plugin.PluginManager;

import com.fullwall.maps.MapOS;

public interface Listener {
	public void registerEvents(MapOS plugin, PluginManager pm);
}
