package com.fullwall.maps.os;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import com.fullwall.maps.MapController;
import com.fullwall.maps.applications.Application;
import com.fullwall.maps.applications.ApplicationProvider;
import com.fullwall.maps.interfaces.ApplicationController;
import com.fullwall.maps.interfaces.ApplicationController.InterruptReason;
import com.fullwall.maps.interfaces.InstalledApplications;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.interfaces.StateHolder;
import com.fullwall.maps.storage.jnbt.CompoundTag;
import com.fullwall.maps.storage.jnbt.StringTag;
import com.fullwall.maps.storage.jnbt.Tag;
import com.google.common.collect.Maps;

public class OperatingSystem {
    private final ApplicationController applicationController = new SingleApplicationController(this);
    private final InstalledApplications installed;
    private final Screen screen;
    private final StateHolder<Map<String, Tag>> settings;

    public OperatingSystem(MapController controller, Player player, MapView current) {
        this.settings = new NBTStateHolder(player);
        this.screen = new MapScreen(new CanvasScreenRenderer(player, current), settings);
        this.installed = new Installed(controller, settings);

        String load = settings.getGlobalStates().containsKey("application") ? ((StringTag) settings
                .getGlobalStates().get("application")).getValue() : "";
        applicationController.switchApplication(controller.getProvider(load));
    }

    public ApplicationController getApplicationController() {
        return applicationController;
    }

    public InstalledApplications getInstalled() {
        return installed;
    }

    public Map<String, Tag> getLocalApplicationSettings(String name) {
        Tag value = settings.getGlobalStates().get(name);
        if (value != null && value instanceof CompoundTag) {
            return ((CompoundTag) value).getValue();
        }
        return Maps.newHashMap();
    }

    public Screen getScreen() {
        return screen;
    }

    void notifyApplicationSwitched(ApplicationProvider provider, Application application) {
        screen.getRenderController().clearScreen();
        screen.getRenderController().clearAttached();
        screen.getAttachments().clearAttached();
        if (application == null) {
            SpringboardRenderer renderer = new SpringboardRenderer(this.applicationController, this.installed);
            settings.load(renderer);
            screen.getRenderController().attach(renderer);
        } else {
            application.begin();
        }
    }

    public void saveApplication(Application application) {
        settings.store(application);
    }

    public void shutdown() {
        this.applicationController.endSession(InterruptReason.Shutdown);
        this.settings.save();
    }
}