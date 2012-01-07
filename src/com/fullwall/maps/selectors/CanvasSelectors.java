package com.fullwall.maps.selectors;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.fullwall.maps.interfaces.Screen;

public class CanvasSelectors {
	private CanvasSelectors() {
		// Utility class
	}

	public static class BoxSelector implements CanvasSelector {
		private final int width;
		private final int height;

		private BoxSelector(int width, int height) {
			this.width = width;
			this.height = height;
		}

		@Override
		public SelectedArea select(Screen screen, int x, int y) {
			byte[] pixels = screen.getRenderController().getScreenRaw();
			byte[] selected = new byte[width * height];
			for (int i = 0; i < width; ++i) {
				for (int j = 0; j < height; ++j) {
					selected[i + j * height] = pixels[(i + x)
							+ (j * height + y)];
				}
			}
			return new BoxSelectedArea(selected, width, height, x, y);
		}

		private static class BoxSelectedArea implements SelectedArea {
			private final byte[] selected;
			private final int width;
			private final int height;
			private int x;
			private int y;

			private BoxSelectedArea(byte[] pixels, int width, int height,
					int x, int y) {
				this.selected = pixels;
				this.width = width;
				this.height = height;
				this.x = x;
				this.y = y;
			}

			@Override
			public SelectedArea fill(byte colour) {
				Arrays.fill(this.selected, colour);
				return this;
			}

			@Override
			public SelectedArea flip() {
				byte[] temp = new byte[selected.length];
				for (int i = 0; i < selected.length; ++i) {
					temp[i] = selected[-i];
				}
				return this;
			}

			@Override
			public SelectedArea outline(byte colour) {
				int x = 0, y = 0;
				for (; x < width; ++x) {
					selected[x] = colour;
				}
				for (; y < height; ++y) {
					selected[x + y * height] = colour;
				}
				int top = y * height;
				for (; x > 0; --x) {
					selected[x + top] = colour;
				}
				for (; y > 0; --y) {
					selected[y * height] = colour;
				}
				return this;
			}

			@Override
			public SelectedArea rotate(double degrees) {
				throw new UnsupportedOperationException();
			}

			private byte[] scale(byte[] data, double scale) {
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				try {
					BufferedImage img = ImageIO.read(in);
					Image scaledImage = img.getScaledInstance(
							(int) (width * scale), (int) (height * scale),
							Image.SCALE_SMOOTH);
					BufferedImage imageBuff = new BufferedImage(
							(int) (width * scale), (int) (height * scale),
							BufferedImage.TYPE_INT_RGB);
					imageBuff.getGraphics().drawImage(scaledImage, 0, 0,
							new Color(0, 0, 0), null);

					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					ImageIO.write(imageBuff, "jpg", buffer);

					return buffer.toByteArray();
				} catch (IOException e) {
					return data;
				}
			}

			@Override
			public SelectedArea scale(double scale) {
				return new BoxSelectedArea(scale(selected, scale),
						(int) (width * scale), (int) (height * scale), x, y);
			}

			@Override
			public SelectedArea translate(int x, int y) {
				this.x += x;
				this.y += y;
				return this;
			}

			@Override
			public void write(Screen screen) {
				byte[] pixels = screen.getRenderController().getScreenRaw();
				for (int i = 0; i < width; ++i) {
					for (int j = 0; j < height; ++j) {
						pixels[(i + x) + (j * height + y)] = selected[i + j
								* height];
					}
				}
			}
		}
	}

	public static BoxSelector newBoxSelector(int width, int height) {
		return new BoxSelector(width, height);
	}
}
