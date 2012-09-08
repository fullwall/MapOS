package com.fullwall.maps.interfaces;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.listeners.PlayerListen.Key;
import com.fullwall.maps.listeners.PlayerListen.MouseClick;
import com.fullwall.maps.os.MapScreen.Mouse;

/**
 * Represents a screen, which contains a number of entities to facilitate
 * different uses.
 * 
 * Controls a mouse, which can be moved around the screen and accessed by code.
 * Screens also forward different events such as render events and mouse events
 * to different components.
 * 
 * Renderers and attachments can be added to the screen via the render
 * controller and attachment manager.
 * 
 * @see ScreenAttachments
 * @see ScreenRenderer
 * 
 * @author fullwall
 */
public interface Screen extends Saveable, Loadable, MouseController<Mouse> {
    void forwardKeyEvent(Key key, boolean pressed);

    void forwardMouseEvent(MouseClick click, boolean pressed);

    /**
     * Notify contained objects of a render event, using the given canvas.
     */
    void forwardRender(MapCanvas canvas);

    /**
     * Returns the screen's @link ScreenAttachments.
     */
    ScreenAttachments getAttachments();

    /**
     * Returns the screen's @link ScreenRenderer.
     */
    ScreenRenderer getRenderController();
}