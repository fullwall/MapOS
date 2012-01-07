package com.fullwall.maps.applications;

import org.bukkit.entity.Player;

public abstract class AbstractApplication implements Application {
	@Override
	public void onCommand(Player player, String command, String[] args) {
	}

	@Override
	public void resume() {
	}
}
