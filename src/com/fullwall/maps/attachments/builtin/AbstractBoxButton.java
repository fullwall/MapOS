package com.fullwall.maps.attachments.builtin;

import com.fullwall.maps.attachments.Clickable;
import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.shapes.Rectangle;

public abstract class AbstractBoxButton implements Clickable {
	private final ClickableArea area;

	protected AbstractBoxButton(int x, int y, int width, int height) {
		this(new Rectangle(x, y, width, height));
	}

	protected AbstractBoxButton(Rectangle box) {
		this.area = new ClickableShape(box);
	}

	@Override
	public ClickableArea getClickableArea() {
		return area;
	}
}
