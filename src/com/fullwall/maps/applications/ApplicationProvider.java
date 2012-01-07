package com.fullwall.maps.applications;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import com.fullwall.maps.applications.ApplicationIcon.IconScale;
import com.fullwall.maps.os.OperatingSystem;
import com.fullwall.maps.storage.NBTStorage;
import com.google.common.collect.Maps;

public class ApplicationProvider {
	private final Map<IconScale, ApplicationIcon> icons = Maps
			.newEnumMap(IconScale.class);
	private final Set<Capability> capabilities;
	private final ApplicationFactory factory;
	private final String name;
	private final Collection<String> commands;

	public ApplicationProvider(ApplicationFactory provider) {
		this.name = provider.getName().toLowerCase();
		this.factory = provider;
		this.commands = provider.getCommands();
		provider.loadGlobalSettings(new NBTStorage(new File(
				"plugins/MapOS/data/apps/" + name + ".dat")));
		this.capabilities = provider.getCapabilities();
		for (IconScale scale : IconScale.values())
			this.icons.put(scale, provider.getIcon(scale));
	}

	public boolean canUse(Player player) {
		return Capability.allow(player, capabilities);
	}

	public Application create(OperatingSystem os) {
		return factory.create(os, os.getLocalApplicationSettings(name));
	}

	public Collection<String> getCommands() {
		return this.commands;
	}

	public ApplicationIcon getIcon(IconScale scale) {
		return icons.get(scale);
	}

	public String getName() {
		return this.name;
	}
}