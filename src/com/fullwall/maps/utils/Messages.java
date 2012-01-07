package com.fullwall.maps.utils;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Messages {

	private Messages() {
	}
	public static final String noPermissionsMessage = ChatColor.RED
			+ "You don't have permission to do that.";
	public static final String notEnoughMoneyMessage = ChatColor.GRAY
			+ "You don't have enough money to do that.";
	public static final String mustBeIngameMessage = "You must use this command ingame";
	public static final String invalidItemIDMessage = ChatColor.RED
			+ "That is not a valid item ID.";

	public static final String noEconomyMessage = ChatColor.GRAY
			+ "This server is not using an economy plugin.";

	public static String getMaterialName(int itemID) {
		String[] parts = Material.getMaterial(itemID).name().toLowerCase()
				.split("_");
		int count = 0;
		for (String s : parts) {
			parts[count] = Strings.capitalise(s);
			if (count < parts.length - 1) {
				parts[count] += " ";
			}
			++count;
		}
		return Arrays.toString(parts).replace("[", "").replace("]", "")
				.replace(", ", "");
	}

	// Get the max-pages message
	public static String getMaxPagesMessage(int page, int maxPages) {
		return ChatColor.GRAY + "The total number of pages is "
				+ Strings.wrap(maxPages, ChatColor.GRAY) + ", page "
				+ Strings.wrap(page, ChatColor.GRAY) + " is not available.";
	}

	public static String getStackString(ItemStack item) {
		return getStackString(item, ChatColor.YELLOW);
	}

	public static String getStackString(ItemStack stack, ChatColor colour) {
		if (stack == null) {
			return "";
		}
		return Strings.wrap(Strings.pluralise(stack.getAmount() + " "
				+ getMaterialName(stack.getTypeId()), stack.getAmount()),
				colour);
	}
}