package com.fullwall.maps.applications;

import org.bukkit.entity.Player;

import com.fullwall.maps.interfaces.ApplicationController.InterruptReason;
import com.fullwall.maps.interfaces.Saveable;

public interface Application extends Saveable {
    void begin();

    String getAppName();

    void interrupt(InterruptReason reason);

    void onCommand(Player player, String command, String[] args);

    void resume();
}
