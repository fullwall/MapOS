package com.fullwall.maps.os;

import java.util.Collection;
import java.util.Set;

import org.bukkit.event.player.PlayerChatEvent;

import com.fullwall.maps.applications.Application;
import com.google.common.collect.Sets;

public class Commands {
	private final Set<String> commands = Sets.newHashSet();
	private Application notify = null;

	void clear() {
		commands.clear();
		notify = null;
	}

	void process(PlayerChatEvent event) {
		if (notify == null || !event.getMessage().startsWith(command))
			return;
		String message = event.getMessage()
				.substring(command.length(), event.getMessage().length())
				.toLowerCase();
		if (message.isEmpty())
			return;
		String[] split = message.split(" ");
		if (!commands.contains(split[0]))
			return;
		event.setCancelled(true);
		String[] args = new String[split.length - 1];
		System.arraycopy(split, 1, args, 0, split.length - 1);

		notify.onCommand(event.getPlayer(), split[0], args);
	}

	void register(Collection<String> commands, Application application) {
		if (commands == null || application == null)
			return;
		for (String command : commands) {
			this.commands.add(command.toLowerCase());
		}
		notify = application;
	}

	private static final String command = ">";
}
