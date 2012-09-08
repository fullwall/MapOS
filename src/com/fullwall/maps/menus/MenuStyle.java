package com.fullwall.maps.menus;

import org.bukkit.map.MapCanvas;

public interface MenuStyle {
    Padding defaultPadding();

    int getItemSpacing();

    void renderIndentation(MapCanvas canvas, int x, int y, int indentationLevel);

    public static MenuStyle Default = new DefaultMenuStyle();
}
