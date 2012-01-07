package com.fullwall.maps.interfaces;

import org.bukkit.map.MapCanvas;

/**
 * Represents an object which can render its state to a screen or canvas.
 * 
 * @author fullwall
 */
public interface Renderable {
	/**
	 * Render to a given screen and canvas.
	 */
	void render(Screen screen, MapCanvas canvas);
}
