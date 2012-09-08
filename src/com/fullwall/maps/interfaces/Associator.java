package com.fullwall.maps.interfaces;

import java.util.Collection;

import com.fullwall.maps.interfaces.Associatables.Associatable;

/**
 * Represents an associator, which can associate an object or class with any
 * number of child objects. Other objects can mark certain objects as being
 * associated with others. These associations can be used for bulk removal at a
 * later time.
 * 
 * @param <T>
 *            the type to perform associations with
 */
public interface Associator<T> {
    void associate(Associatable<T> associated, T association);

    void associate(Class<? extends Associatable<T>> association, T associated);

    void clearAssociations();

    Collection<T> getAssociations(Associatable<T> association);

    Collection<T> getAssociations(Class<? extends Associatable<T>> association);

    boolean isAssociated(Associatable<T> association);

    boolean isAssociated(Class<? extends Associatable<T>> association);

    void remove(Associatable<T> association);

    void remove(Class<? extends Associatable<T>> association);
}