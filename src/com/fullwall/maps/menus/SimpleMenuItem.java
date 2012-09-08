package com.fullwall.maps.menus;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.attachments.MouseClickEvent;
import com.fullwall.maps.attachments.MouseMoveEvent;
import com.fullwall.maps.attachments.builtin.ClickableShape;
import com.fullwall.maps.attachments.builtin.EmptyClickableArea;
import com.fullwall.maps.shapes.Rectangle;
import com.fullwall.maps.utils.MapString;

public abstract class SimpleMenuItem implements MenuItem {
    private ClickableArea box = new EmptyClickableArea();
    private final MenuRenderer renderer;
    private final MapString text;

    public SimpleMenuItem(Menu parent, MapString text) {
        this.text = text;
        this.renderer = parent.getRenderer();
    }

    @Override
    public ClickableArea getClickableArea() {
        return box;
    }

    @Override
    public int getHeight() {
        return text.getHeight();
    }

    protected abstract void onClick();

    @Override
    public void onMouseDown(MouseClickEvent event) {
        onClick();
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
    }

    @Override
    public void onMouseUp(MouseClickEvent event) {
    }

    @Override
    public void render(MapCanvas canvas, int currentX, int currentY) {
        canvas.drawText(currentX, currentY, text.getFont(), text.toString());
        box = new ClickableShape(new Rectangle(currentX, currentY, text.getWidth(), text.getHeight()));
    }

    protected void rerender() {
        renderer.beginRendering();
    }
}
