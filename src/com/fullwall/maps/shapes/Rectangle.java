package com.fullwall.maps.shapes;

import com.fullwall.maps.shapes.Outliner.PrimitiveDrawer;
import com.fullwall.maps.utils.Point;

public class Rectangle implements Shape {
	private final int x, y, width, height, maxX, maxY;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.maxX = x + width;
		this.maxY = y + height;
	}

	@Override
	public boolean interescts(int x, int y) {
		return x > this.x && maxX > x && y > this.y && maxY > y;
	}

	@Override
	public void outline(PrimitiveDrawer drawer) {
		drawer.drawLine(x, y, x, y + height);
		drawer.drawLine(x, y + height, x + width, y + height);
		drawer.drawLine(x + width, y + height, x + width, y);
		drawer.drawLine(x + width, y, x, y);
	}

	@Override
	public Rectangle rotate(double degrees) {
		Point rotated = ShapeRotations.rotate(degrees, new Point(x, y));
		return new Rectangle(rotated.getX(), rotated.getY(), width, height);
	}

	@Override
	public Rectangle scale(double scale) {
		return new Rectangle(x, y, (int) (width * scale),
				(int) (height * scale));
	}

	@Override
	public Rectangle translate(int x, int y) {
		return new Rectangle(this.x + x, this.y + y, width, height);
	}
}
