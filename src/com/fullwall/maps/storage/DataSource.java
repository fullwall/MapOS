package com.fullwall.maps.storage;

public interface DataSource {
	public DataKey getKey(String root);

	public void load();

	public void save();
}
