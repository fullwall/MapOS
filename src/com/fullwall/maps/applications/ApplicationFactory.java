package com.fullwall.maps.applications;

import java.util.Map;
import java.util.Set;

import com.fullwall.maps.applications.ApplicationIcon.IconScale;
import com.fullwall.maps.os.OperatingSystem;
import com.fullwall.maps.storage.NBTStorage;
import com.fullwall.maps.storage.jnbt.Tag;

public interface ApplicationFactory {
    public Application create(OperatingSystem os, Map<String, Tag> localSettings);

    public Set<Capability> getCapabilities();

    public ApplicationIcon getIcon(IconScale scale);

    public String getName();

    public void loadGlobalSettings(NBTStorage storage);
}
