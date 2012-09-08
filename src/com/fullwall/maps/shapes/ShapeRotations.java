package com.fullwall.maps.shapes;

import com.fullwall.maps.utils.Point;

public class ShapeRotations {
    private static double checkedCos(double degrees) {
        return (Math.toDegrees(Math.cos(Math.toRadians(degrees))));
    }

    private static double checkedSine(double degrees) {
        return (Math.toDegrees(Math.sin(Math.toRadians(degrees))));
    }

    public static Point rotate(double degrees, int x, int y) {
        return rotate(degrees, x, y);
    }

    public static Point[] rotate(double degrees, Point... points) {
        return rotate(degrees, new Point[points.length], points);
    }

    public static Point rotate(double degrees, Point point) {
        return rotatePoint(degrees, point);
    }

    private static Point[] rotate(double degrees, Point[] points, Point[] source) {
        for (int i = 0; i < points.length; ++i) {
            points[i] = rotatePoint(degrees, source[i]);
        }
        return points;
    }

    private static Point rotatePoint(double degrees, int x, int y) {
        return new Point((int) (x * checkedCos(degrees) - y * checkedSine(degrees)), (int) (x
                * checkedSine(degrees) + y * checkedCos(degrees)));
    }

    private static Point rotatePoint(double degrees, Point point) {
        return rotatePoint(degrees, point.getX(), point.getY());
    }
}
