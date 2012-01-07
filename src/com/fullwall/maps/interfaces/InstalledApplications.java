package com.fullwall.maps.interfaces;

import com.fullwall.maps.applications.ApplicationProvider;

public interface InstalledApplications {
	public ApplicationProvider getInstalled(int index);

	public ApplicationProvider getInstalled(String name);

	public void install(String name);

	public boolean isInstalled(String name);

	public int size();

	public void uninstall(String name);
}