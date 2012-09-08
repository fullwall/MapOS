package com.fullwall.maps.shapes;

public class LineEquations {
    public static abstract class LineEquation {
        public abstract double calculate(double x);

        public abstract int getGradient();

        public boolean isHorizontal() {
            return false;
        }

        public boolean isVertical() {
            return false;
        }
    }

    private static final LineEquation horizontal = new LineEquation() {
        @Override
        public double calculate(double x) {
            throw new UnsupportedOperationException("y is constant");
        }

        @Override
        public int getGradient() {
            throw new UnsupportedOperationException("undefined for horizontal lines");
        }

        @Override
        public boolean isHorizontal() {
            return true;
        }

    };
    private static final LineEquation vertical = new LineEquation() {
        @Override
        public double calculate(double x) {
            throw new UnsupportedOperationException("vertical line");
        }

        @Override
        public int getGradient() {
            return 0;
        }

        @Override
        public boolean isVertical() {
            return true;
        }
    };

    public static LineEquation create(int x1, int y1, int x2, int y2) {
        if (x2 - x1 == 0) {
            return vertical;
        }
        final int gradient = (x2 - x1) / (y2 - y1);
        if (gradient == 0)
            return horizontal;
        final int c = y1 - gradient * x1;
        return new LineEquation() {
            @Override
            public double calculate(double x) {
                return gradient * x + c;
            }

            @Override
            public int getGradient() {
                return gradient;
            }
        };
    }
}
