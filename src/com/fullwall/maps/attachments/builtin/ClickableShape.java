package com.fullwall.maps.attachments.builtin;

import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.attachments.MouseEvent;
import com.fullwall.maps.shapes.Shape;

public class ClickableShape implements ClickableArea {
	private final Shape shape;

	public ClickableShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public boolean intersects(MouseEvent event) {
		return shape.interescts(event.getMouse().getX(), event.getMouse().getY());
	}
}
