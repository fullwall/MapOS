package com.fullwall.maps.attachments;

import java.util.Collection;

/**
 * Represents an object that can have other objects attached to it, where they
 * can be operated upon. A surface can have multiple objects of the given type
 * attached.
 * 
 * @author fullwall
 * 
 * @param <T>
 *            the object type that can be attached to this
 */
public interface AttachableSurface<T> {
    /**
     * Attaches an object to the surface.
     * 
     * @param toAttach
     *            the object to attach
     */
    void attach(T toAttach);

    /**
     * Attaches all of the objects within the collection.
     * 
     * @param toAttach
     *            the collection of objects to attach
     */
    void attachAll(Collection<T> toAttach);

    /**
     * Detaches all attachments.
     */
    void clearAttached();

    /**
     * Returns whether an attachment of the given class has been attached.
     */
    boolean contains(Class<? extends T> attachmentClass);

    /**
     * Returns whether the given attachment has been attached or not.
     */
    boolean contains(T attachment);

    /**
     * Removes an object that may or may not have been previously attached.
     * 
     * @param previous
     *            the attached object to remove
     */
    void remove(T previous);

    /**
     * Removes all of the previously attached objects within a collection.
     * 
     * @param previous
     *            the collection of objects to detach
     */
    void removeAll(Collection<T> previous);
}
