package com.fullwall.maps.menus;

import org.bukkit.map.MapCanvas;

public class DefaultMenuStyle implements MenuStyle {
	@Override
	public Padding defaultPadding() {
		return Default;
	}

	@Override
	public int getItemSpacing() {
		return 1;
	}

	@Override
	public void renderIndentation(MapCanvas canvas, int x, int y,
			int indentationLevel) {
	}

	private static final Padding Default = new Padding(5, 5);
}
