package com.fullwall.maps.interfaces;

import com.fullwall.maps.storage.NBTMemoryStorage.NBTKey;

/**
 * Represents an object which can save its state.
 * 
 * @author fullwall
 */
public interface Saveable {
	void save(NBTKey root);

	String getRootName();
}
