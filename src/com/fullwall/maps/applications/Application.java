package com.fullwall.maps.applications;

import org.bukkit.entity.Player;

import com.fullwall.maps.interfaces.ApplicationController.InterruptReason;
import com.fullwall.maps.interfaces.Saveable;

public interface Application extends Saveable {
	void interrupt(InterruptReason reason);

	void begin();

	void resume();

	String getAppName();

	void onCommand(Player player, String command, String[] args);
}
