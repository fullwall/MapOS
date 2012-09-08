package com.fullwall.maps.shapes;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.utils.Point;

public class Outliner implements Drawer {
    private final Shape shape;
    private final int thickness;

    private Outliner(Shape shape, int thickness) {
        this.shape = shape;
        this.thickness = thickness;
    }

    @Override
    public Outliner draw(MapCanvas canvas, byte colour) {
        shape.outline(new PrimitiveDrawer(canvas, colour));
        return this;
    }

    public class PrimitiveDrawer {
        private final MapCanvas canvas;
        private final byte colour;

        private PrimitiveDrawer(MapCanvas canvas, byte colour) {
            this.canvas = canvas;
            this.colour = colour;
        }

        public void drawCircle(int centerX, int centerY, int radius) {
            int error = -radius, x = radius, y = 0;
            while (x > y) {
                drawCirclePoints(centerX, centerY, x, y);
                drawCirclePoints(centerX, centerY, y, x);

                error += y;
                ++y;
                error += y;
                if (error >= 0) {
                    --x;
                    error -= x * 2;
                }
            }
            drawCirclePoints(centerX, centerY, x, y);
        }

        private void drawCirclePoints(int centerX, int centerY, int x, int y) {
            canvas.setPixel(centerX + x, centerY + y, colour);
            if (x != 0)
                canvas.setPixel(centerX - x, centerY + y, colour);
            if (y != 0)
                canvas.setPixel(centerX + x, centerY - y, colour);
            canvas.setPixel(centerX - x, centerY - y, colour);
        }

        public void drawLine(int x1, int y1, int x2, int y2) {
            int halved = thickness / 2;
            int offsetLow = -halved, offsetHigh = halved;
            if (thickness % 2 == 0)
                --offsetHigh;
            int current = offsetHigh;
            while (current != offsetLow) {
                drawLine0(x1, y1 + current, x2, y2 + current);
                --current;
            }
        }

        public void drawLine(Point p1, Point p2) {
            drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        private void drawLine0(int x1, int y1, int x2, int y2) {
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int sx = 1, sy = 1, e2;
            int error = dx - dy;
            if (x1 > x2)
                sx = -1;
            if (y1 > y2)
                sy = -1;
            while (true) {
                canvas.setPixel(x1, y1, colour);
                if (x1 == x2 && y1 == y2)
                    break;
                e2 = error * 2;
                if (e2 > -dy) {
                    error -= dy;
                    x1 += sx;
                }
                if (e2 < dx) {
                    error += dx;
                    y1 += sy;
                }
            }
        }
    }

    public static Outliner create(Shape shape) {
        return createWithThickness(shape, 1);
    }

    public static Outliner createWithThickness(Shape shape, int thickness) {
        if (thickness <= 0)
            throw new IllegalArgumentException("thickness is too small");
        return new Outliner(shape, thickness);
    }
}
