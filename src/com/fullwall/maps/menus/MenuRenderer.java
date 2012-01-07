package com.fullwall.maps.menus;

import com.fullwall.maps.applications.CanvasRenderer;
import com.fullwall.maps.interfaces.DelayedRenderer;

public interface MenuRenderer extends CanvasRenderer, DelayedRenderer {
	@Override
	void beginRendering();

	int getIndent();

	void setIndent(int indent);
}
