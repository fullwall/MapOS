package com.fullwall.maps.shapes;

import com.fullwall.maps.shapes.Outliner.PrimitiveDrawer;

public class Circle implements Shape {
    private final int centerX;
    private final int centerY;
    private final int radius;

    public Circle(int centerX, int centerY, int radius) {
        if (radius <= 0 || centerX < 0 || centerY < 0)
            throw new IllegalArgumentException("args cannot be less than 0");
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public boolean interescts(int x, int y) {
        double distance = Math.pow((centerX - x), 2) + Math.pow((this.centerY - y), 2);
        return distance < Math.pow(this.radius, 2);
    }

    @Override
    public void outline(PrimitiveDrawer drawer) {
        drawer.drawCircle(centerX, centerY, radius);
    }

    @Override
    public Circle rotate(double degrees) {
        return this; // rotating circles gives the same circle.
    }

    @Override
    public Circle scale(double scale) {
        return new Circle(centerX, centerY, (int) (radius * scale));
    }

    @Override
    public Circle translate(int x, int y) {
        return new Circle(centerX + x, centerY + y, radius);
    }
}
