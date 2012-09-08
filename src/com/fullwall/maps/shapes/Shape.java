package com.fullwall.maps.shapes;

import com.fullwall.maps.shapes.Outliner.PrimitiveDrawer;

public interface Shape {
    boolean interescts(int x, int y);

    void outline(PrimitiveDrawer drawer);

    Shape rotate(double degrees);

    Shape scale(double scale);

    Shape translate(int x, int y);
}
