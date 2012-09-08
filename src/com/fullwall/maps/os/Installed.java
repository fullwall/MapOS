package com.fullwall.maps.os;

import java.util.Map;

import com.fullwall.maps.MapController;
import com.fullwall.maps.applications.ApplicationProvider;
import com.fullwall.maps.interfaces.InstalledApplications;
import com.fullwall.maps.interfaces.Loadable;
import com.fullwall.maps.interfaces.Saveable;
import com.fullwall.maps.interfaces.StateHolder;
import com.fullwall.maps.storage.DataKey;
import com.fullwall.maps.storage.jnbt.Tag;
import com.fullwall.maps.utils.ListSet;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class Installed implements Saveable, Loadable, InstalledApplications {
    private final MapController controller;
    private final ListSet<String> installed = new ListSet<String>();

    Installed(MapController controller, StateHolder<Map<String, Tag>> settings) {
        settings.addLoader(this);
        settings.addSaver(this);
        this.controller = controller;
        installed.addAll(MapController.getDefaultProviders().keySet());
    }

    @Override
    public ApplicationProvider getInstalled(int index) {
        return controller.getProvider(installed.get(index));
    }

    @Override
    public ApplicationProvider getInstalled(String name) {
        if (!installed.contains(name))
            throw new IllegalArgumentException("not installed");
        return controller.getProvider(name);
    }

    @Override
    public String getRootName() {
        return "installed";
    }

    @Override
    public void install(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("invalid provider name");
        if (installed.contains(name))
            throw new IllegalArgumentException("application already installed");
        installed.add(name);
    }

    @Override
    public boolean isInstalled(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("invalid name");
        return installed.contains(name);
    }

    @Override
    public void load(DataKey root, boolean empty) {
        if (empty)
            return;
        for (String name : Splitter.on(";").split(root.getString("apps"))) {
            if (controller.getProvider(name) == null)
                continue;
            installed.add(name);
        }
    }

    @Override
    public void save(DataKey root) {
        root.setString("apps", Joiner.on(";").join(installed.asList()));
    }

    @Override
    public int size() {
        return installed.size();
    }

    @Override
    public void uninstall(String name) {
        if (!installed.contains(name))
            throw new IllegalArgumentException("not installed");
        installed.remove(name);
    }
}
