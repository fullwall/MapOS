package com.fullwall.maps.utils;

import com.fullwall.maps.os.Constants;

/**
 * Standard 2D point implementation.
 * 
 * @author fullwall
 * 
 */
public class Point {
	private final int x;
	private final int y;

	public Point(int x, int y) {
		if (x > Constants.MaxMapCoordinate || y > Constants.MaxMapCoordinate)
			throw new IllegalArgumentException("point is outside map range");
		this.x = x;
		this.y = y;
	}

	public java.awt.Point asPoint() {
		return new java.awt.Point(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public int hashCode() {
		return 31 * (31 + x) + y;
	}
}
