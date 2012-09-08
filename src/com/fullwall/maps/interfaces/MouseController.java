package com.fullwall.maps.interfaces;

/**
 * Represents an object that controls a mouse. The type parameter need not
 * specify the direct type of the mouse - only some representation of it that
 * can be returned to the user.
 * 
 * @author fullwall
 * @param <T>
 *            the mouse object type.
 */
public interface MouseController<T> {
    /**
     * Returns the mouse controlled by this controller.
     */
    T getMouse();

    /**
     * Moves the mouse an arbitrary amount horizontally and vertically.
     * 
     * @param horizontal
     * @param vertical
     */
    void moveMouse(int horizontal, int vertical);
}
