package com.fullwall.maps.interfaces;

import com.fullwall.maps.storage.DataKey;

/**
 * Represents an object which can save its state.
 * 
 * @author fullwall
 */
public interface Saveable {
    String getRootName();

    void save(DataKey root);
}
