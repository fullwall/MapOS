package com.fullwall.maps.attachments;

/**
 * Represents an arbitrary area that can be clicked on.
 * 
 * @author fullwall
 */
public interface ClickableArea {
	/**
	 * @param event
	 *            the mouse click to be tested.
	 * @return whether the click lies within the clickable area represented by
	 *         this object.
	 */
	boolean intersects(MouseEvent event);
}
