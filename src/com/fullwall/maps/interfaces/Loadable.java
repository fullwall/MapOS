package com.fullwall.maps.interfaces;

import com.fullwall.maps.storage.DataKey;

/**
 * Represents an object whose state can be loaded from NBT storage.
 * 
 * @author fullwall
 */
public interface Loadable {
    /**
     * Returns the root name to use while fetching settings.
     */
    String getRootName();

    /**
     * Load, using the given settings.
     * 
     * @param settings
     * @param empty
     *            whether the settings are empty (ie. they did not exist before)
     */
    void load(DataKey root, boolean empty);
}
