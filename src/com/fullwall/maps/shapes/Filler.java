package com.fullwall.maps.shapes;

import java.util.ArrayDeque;
import java.util.Deque;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.utils.Point;

public class Filler {
	private static class FloodFiller implements Drawer {
		private final Point start;
		private final Deque<Point> queue = new ArrayDeque<Point>();
		private final byte except;
		private final boolean useExcept;

		private FloodFiller(int x, int y, byte except, boolean useExcept) {
			this.except = except;
			this.useExcept = useExcept;
			this.start = new Point(x, y);
		}

		@Override
		public FloodFiller draw(MapCanvas canvas, byte colour) {
			queue.clear();
			queue.add(start);
			while (!queue.isEmpty()) {
				Point point = queue.pop();
				out: if (point.getX() - 1 >= 0) {
					byte left = canvas.getPixel(point.getX() - 1, point.getY());
					if (stop(left))
						break out;
					canvas.setPixel(point.getX() - 1, point.getY(), colour);
					int start = point.getX() - 1;
					while (start >= 0
							&& !stop(canvas.getPixel(start, point.getY()))) {
						canvas.setPixel(start, point.getY(), colour);
						queue.add(new Point(start, point.getY()));
						--start;
					}
				} else if (point.getX() + 1 > 127)
					continue;
				byte right = canvas.getPixel(point.getX() - 1, point.getY());
				if (stop(right))
					continue;
				canvas.setPixel(point.getX() + 1, point.getY(), colour);
				int start = point.getX() + 1;
				while (start <= 127
						&& !stop(canvas.getPixel(start, point.getY()))) {
					canvas.setPixel(start, point.getY(), colour);
					queue.add(new Point(start, point.getY()));
					++start;
				}
			}
			return this;
		}

		private boolean stop(byte pixel) {
			return useExcept ? pixel == except : pixel != except;
		}
	}

	public static Drawer fillExcept(int x, int y, byte colour) {
		return new FloodFiller(x, y, colour, true);
	}

	public static Drawer replaceFill(int x, int y, byte replace) {
		return new FloodFiller(x, y, replace, false);
	}
}
