package com.fullwall.maps.os;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import com.fullwall.maps.MapController;

public class ForwardingMapRenderer extends MapRenderer {
    private final MapController controller;

    public ForwardingMapRenderer(MapController controller) {
        this.controller = controller;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        if (!controller.hasSystem(player))
            return;
        controller.getSystem(player).getScreen().forwardRender(canvas);
    }
}
