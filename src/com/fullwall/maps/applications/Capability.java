package com.fullwall.maps.applications;

import java.util.Collection;
import java.util.Set;

import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

/**
 * A capability is a specific feature or requirement of an application.
 * Capabilities define a permission that players must have to use.
 * 
 * @author fullwall
 */
public enum Capability {
    FileDownloading("internet.access.files.download"),
    FileSystemAccess("filesystem.access"),
    FileUploading("internet.access.files.upload"),
    InternetAccess("internet.access");
    private String perm;

    Capability(String perm) {
        this.perm = ".capabilities." + perm;
    }

    private boolean hasPermission(Player player) {
        return player.hasPermission(perm);
    }

    public static final Set<Capability> NONE = Sets.newHashSet();

    public static boolean allow(Player player, Collection<Capability> capabilities) {
        for (Capability capability : capabilities) {
            if (!capability.hasPermission(player))
                return false;
        }
        return true;
    }
}
