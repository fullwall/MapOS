package com.fullwall.maps.attachments;

/**
 * A representation of a clickable area. Extends MouseListener. Mouse events
 * will only be called if the click intersects the given ClickableArea.
 * 
 * @see MouseListener
 * @see
 * @author fullwall
 */
public interface Clickable extends MouseListener {
    /**
     * Returns the area that can be clicked. The intersects method will be
     * called to determine whether a mouse event should be forwarded.
     * 
     * @see ClickableArea#intersects(MouseClickEvent);
     * @return the area that can be clicked.
     */
    ClickableArea getClickableArea();
}
