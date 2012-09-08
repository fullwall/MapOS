package com.fullwall.maps.selectors;

import com.fullwall.maps.interfaces.Screen;

public interface SelectedArea {
    SelectedArea fill(byte colour);

    SelectedArea flip();

    SelectedArea outline(byte colour);

    SelectedArea rotate(double degrees);

    SelectedArea scale(double scale);

    SelectedArea translate(int x, int y);

    void write(Screen screen);
}
