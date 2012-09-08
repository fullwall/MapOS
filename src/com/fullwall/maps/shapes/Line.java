package com.fullwall.maps.shapes;

import com.fullwall.maps.shapes.LineEquations.LineEquation;
import com.fullwall.maps.shapes.Outliner.PrimitiveDrawer;
import com.fullwall.maps.utils.Point;

public class Line implements Shape {
    private final LineEquation equation;
    private final Point p1, p2;

    public Line(int x1, int y1, int x2, int y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.equation = LineEquations.create(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override
    public boolean interescts(int x, int y) {
        return (equation.isHorizontal() && y == this.p1.getY())
                || (equation.isVertical() && x == this.p1.getX()) || y == equation.calculate(x);
    }

    @Override
    public void outline(PrimitiveDrawer drawer) {
        drawer.drawLine(p1, p2);
    }

    @Override
    public Line rotate(double degrees) {
        Point[] rotated = ShapeRotations.rotate(degrees, p1, p1);
        return new Line(rotated[0], rotated[1]);
    }

    @Override
    public Line scale(double scale) {
        int newY = (int) (equation.isHorizontal() ? p1.getY() : equation.isVertical() ? p2.getY() * scale
                : equation.calculate(p2.getX() * scale));
        return new Line(p1.getX(), p1.getY(), (int) (p2.getX() * scale), newY);
    }

    @Override
    public Line translate(int x, int y) {
        return new Line(p1.getX() + x, p1.getY() + y, p2.getX() + x, p2.getY() + y);
    }
}
