package com.fullwall.maps.applications;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.os.Constants;
import com.fullwall.maps.utils.MapColour;
import com.fullwall.maps.utils.MapString;

public class ApplicationIcon {
	private final String identifier;
	private final byte[] icon;
	private final IconScale scale;

	public ApplicationIcon(String identifier, IconScale scale, byte[] icon) {
		this.identifier = identifier;
		this.scale = scale;
		this.icon = icon;
	}

	public boolean conforms(IconScale check) {
		return this.icon.length == scale.expectedLength;
	}

	public String getName() {
		return this.identifier;
	}

	public void render(int x, int y, MapCanvas canvas) {
		for (int i = 0; i < scale.expectedWidth; ++i) {
			for (int j = 0; j < scale.expectedHeight; ++j) {
				byte colour = icon[i + j * scale.expectedHeight];
				canvas.setPixel(i + x, j + y, colour);
			}
		}
		MapString string = new MapString(Constants.Font, identifier).colour(
				MapColour.White).truncate(scale.expectedWidth);
		canvas.drawText(x, y + scale.expectedHeight + 1, string.getFont(),
				string.toString());
	}

	public enum IconScale {
		Layout(28, 28),
		Thumbnail(12, 12);
		private final int expectedHeight;
		private final int expectedWidth;
		private final int expectedLength;

		IconScale(int expectedHeight, int expectedWidth) {
			this.expectedHeight = expectedHeight;
			this.expectedWidth = expectedWidth;
			this.expectedLength = expectedHeight * expectedWidth;
		}

		public int getArrayLength() {
			return expectedLength;
		}

		public int getHeight() {
			return expectedHeight;
		}

		public int getWidth() {
			return expectedWidth;
		}
	}

	public static boolean conforms(IconScale scale, ApplicationIcon check) {
		return check.conforms(scale);
	}
}
