package com.fullwall.maps.menus;

import com.fullwall.maps.utils.MapString;

public class CenteredTitle implements MenuTitle {
    private final Padding padding;
    private final MapString title;

    public CenteredTitle(MapString title) {
        this.title = title;
        this.padding = new Padding(64 - (title.getWidth() / 2), 5);
    }

    public CenteredTitle(String string) {
        this(new MapString(string));
    }

    @Override
    public MapString getTitle() {
        return title;
    }

    @Override
    public Padding padding() {
        return padding;
    }
}
