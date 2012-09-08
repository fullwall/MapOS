package com.fullwall.maps.utils;

public class MapPixel {
    private final byte colour;
    private final Point point;

    public MapPixel(Point point, byte colour) {
        this.point = point;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MapPixel other = (MapPixel) obj;
        if (colour != other.colour) {
            return false;
        }
        if (point == null) {
            if (other.point != null) {
                return false;
            }
        } else if (!point.equals(other.point)) {
            return false;
        }
        return true;
    }

    public byte getColour() {
        return colour;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public int hashCode() {
        return 31 * (31 + colour) + ((point == null) ? 0 : point.hashCode());
    }
}
