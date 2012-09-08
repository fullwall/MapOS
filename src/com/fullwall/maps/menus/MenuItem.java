package com.fullwall.maps.menus;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.attachments.MouseListener;

public interface MenuItem extends MouseListener {
    ClickableArea getClickableArea();

    int getHeight();

    void render(MapCanvas canvas, int currentX, int currentY);
}
