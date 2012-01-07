package com.fullwall.maps.applications;


public abstract class AbstractCanvasRenderer implements CanvasRenderer {
	@Override
	public boolean clearPixelsOnRemove() {
		return true;
	}

	@Override
	public RenderPriority getPriority() {
		return RenderPriority.Normal;
	}

	@Override
	public boolean isRendering() {
		return true;
	}
}
