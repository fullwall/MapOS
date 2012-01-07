package com.fullwall.maps.sk89q;


public class ServerCommandException extends CommandException {

	public ServerCommandException() {
		super("You must be ingame to perform this command.");
	}

	private static final long serialVersionUID = 9120268556899197316L;
}
