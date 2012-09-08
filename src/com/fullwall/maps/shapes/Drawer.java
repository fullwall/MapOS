package com.fullwall.maps.shapes;

import org.bukkit.map.MapCanvas;

public interface Drawer {
    Drawer draw(MapCanvas canvas, byte colour);
}
