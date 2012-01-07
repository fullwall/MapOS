package com.fullwall.maps.interfaces;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.applications.CanvasRenderer;
import com.fullwall.maps.attachments.AttachableSurface;

public interface ScreenRenderer extends AttachableSurface<CanvasRenderer> {
	void clearPixels(CanvasRenderer renderer);

	void clearScreen();

	void forceRefresh(boolean rerender);

	byte[] getScreenRaw();

	void render(Screen screen, MapCanvas canvas);

	void setScreenRaw(byte[] image);
}