package com.fullwall.maps.shapes;

import java.util.List;

import com.fullwall.maps.shapes.Outliner.PrimitiveDrawer;
import com.fullwall.maps.utils.Point;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class Polygon implements Shape {
	private final int[] xVertices;
	private final int[] yVertices;
	private final int minX, maxX, minY, maxY;
	private final int numVertices;

	public Polygon(int[] xVertices, int[] yVertices) {
		if (xVertices.length != yVertices.length)
			throw new IllegalArgumentException("vertice lengths not equal");
		if (xVertices[-1] != xVertices[0] || yVertices[-1] != yVertices[0])
			throw new IllegalArgumentException("polygon not closed");
		if (xVertices.length < 4)
			throw new IllegalArgumentException(
					"polygon has less than three sides");
		this.xVertices = xVertices;
		this.yVertices = yVertices;
		int minX = 999, maxX = -999, minY = 999, maxY = -999;
		for (int i = 0; i < xVertices.length; ++i) {
			minX = Math.min(minX, xVertices[i]);
			maxX = Math.max(maxX, xVertices[i]);
			minY = Math.min(minY, yVertices[i]);
			maxY = Math.max(maxY, yVertices[i]);
		}
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.numVertices = xVertices.length;
	}

	@Override
	public boolean interescts(int x, int y) {
		if (x < minX || x > maxX || y < minY || y > maxY)
			return false; // don't loop if it's obviously outside.
		boolean intersects = false;
		for (int i = 0, j = numVertices - 1; i < numVertices; j = i++) {
			if ((yVertices[i] > y != yVertices[j] > y)
					&& (x < (xVertices[j] - xVertices[i]) * (y - yVertices[i])
							/ (yVertices[j] - yVertices[i]) + xVertices[i]))
				intersects = !intersects;
		}
		return intersects;
	}

	@Override
	public void outline(PrimitiveDrawer drawer) {
		int[] x = xVertices;
		int[] y = yVertices;
		for (int i = 0; i < x.length - 1; ++i) {
			drawer.drawLine(x[i], y[i], x[i + 1], y[i + 1]);
		}
	}

	@Override
	public Polygon rotate(double degrees) {
		Point[] points = new Point[xVertices.length];
		for (int i = 0; i < points.length; ++i) {
			Point rotated = ShapeRotations.rotate(degrees, new Point(
					xVertices[i], yVertices[i]));
			xVertices[i] = rotated.getX();
			yVertices[i] = rotated.getY();
		}
		return new Polygon(xVertices, yVertices);
	}

	@Override
	public Polygon scale(double scale) {
		for (int i = 0; i < xVertices.length; ++i) {
			xVertices[i] *= scale;
			yVertices[i] *= scale;
		}
		return new Polygon(xVertices, yVertices);
	}

	@Override
	public Polygon translate(int x, int y) {
		for (int i = 0; i < xVertices.length; ++i) {
			xVertices[i] += x;
			yVertices[i] += y;
		}
		return new Polygon(xVertices, yVertices);
	}

	public static class PolygonBuilder {
		private final List<Integer> xVertices = Lists.newArrayList();
		private final List<Integer> yVertices = Lists.newArrayList();

		public Polygon closeAndCreate() {
			xVertices.add(xVertices.get(0));
			yVertices.add(yVertices.get(0));
			return create();
		}

		public Polygon create() {
			return new Polygon(Ints.toArray(xVertices), Ints.toArray(yVertices));
		}

		public PolygonBuilder pushCoord(int x, int y) {
			xVertices.add(x);
			yVertices.add(y);
			return this;
		}
	}
}
