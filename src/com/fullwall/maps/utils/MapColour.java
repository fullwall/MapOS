package com.fullwall.maps.utils;

import org.bukkit.map.MapPalette;

public enum MapColour {
    Blue(MapPalette.BLUE),
    Brown(MapPalette.BROWN),
    DarkBrown(MapPalette.DARK_BROWN),
    DarkGreen(MapPalette.DARK_GREEN),
    DarkGrey(MapPalette.DARK_GRAY),
    Grey(MapPalette.GRAY_1),
    Grey2(MapPalette.GRAY_2),
    LightBrown(MapPalette.LIGHT_BROWN),
    LightGreen(MapPalette.LIGHT_GREEN),
    LightGrey(MapPalette.LIGHT_GRAY),
    PaleBlue(MapPalette.PALE_BLUE),
    Red(MapPalette.RED),
    Transparent(MapPalette.TRANSPARENT),
    White(MapPalette.WHITE);

    private final byte colour;

    MapColour(byte colour) {
        this.colour = colour;
    }

    public byte colour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return '\u00A7' + Byte.toString(colour) + ";";
    }

    public static String strip(String other) {
        String stripped = other;
        while (stripped.indexOf('\u00A7') != -1) {
            int idx = stripped.indexOf('\u00A7');
            int colon = stripped.indexOf(';', idx);
            stripped = stripped.substring(0, idx) + stripped.substring(colon + 1, stripped.length());
        }
        return stripped;
    }

}