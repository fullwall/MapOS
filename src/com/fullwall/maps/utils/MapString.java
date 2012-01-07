package com.fullwall.maps.utils;

import org.bukkit.map.MapFont;

import com.fullwall.maps.os.Constants;

public class MapString {
	private final MapFont font;
	private final int width;
	private final String string;

	public MapString(MapFont font, String string) {
		this.font = font;
		this.string = string.replaceAll("\t", "    ");
		this.width = font.getWidth(MapColour.strip(string));
	}

	public MapString(String string) {
		this(Constants.Font, string);
	}

	public MapString colour(MapColour colour) {
		return create(colour + string);
	}

	public MapString colour(MapColour colour, int atIndex) {
		return create(string.substring(0, atIndex) + colour
				+ string.substring(atIndex + 1, string.length()));
	}

	public MapString concat(MapString other) {
		return create(this.string + other.string);
	}

	public MapString concat(String string) {
		return create(this.string + string);
	}

	private MapString create(String other) {
		return new MapString(font, other);
	}

	public MapFont getFont() {
		return font;
	}

	public int getHeight() {
		return this.font.getHeight();
	}

	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		return this.string;
	}

	public MapString truncate(int targetWidth) {
		String process = string;
		while (process.length() > 0
				&& font.getWidth(MapColour.strip(process)) > targetWidth) {
			process = process.substring(0, process.length() - 1);
		}
		return create(process);
	}
}