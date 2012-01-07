package com.fullwall.maps.menus;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.attachments.MouseClickEvent;
import com.fullwall.maps.attachments.MouseMoveEvent;
import com.fullwall.maps.attachments.builtin.ClickableShape;
import com.fullwall.maps.os.Constants;
import com.fullwall.maps.shapes.Rectangle;
import com.fullwall.maps.utils.MapColour;
import com.fullwall.maps.utils.MapString;

public class MenuSwitcherItem implements MenuItem {
	private final Menu current;
	private final Menu parent;
	private final ClickableArea clickable;

	public MenuSwitcherItem(Menu current, Menu parent) {
		this.current = current;
		this.parent = parent;
		Padding padding = current.getStyle().defaultPadding();
		clickable = new ClickableShape(new Rectangle(padding.left(),
				padding.upper(), text.getWidth(), text.getHeight()));
	}

	@Override
	public ClickableArea getClickableArea() {
		return this.clickable;
	}

	@Override
	public int getHeight() {
		return text.getHeight();
	}

	@Override
	public void onMouseDown(MouseClickEvent event) {
		current.disposeFrom(event.getScreen().getAttachments());
		current.disposeFrom(event.getScreen().getRenderController());
		event.getScreen().getRenderController().clearScreen();
		parent.beginRendering();
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
	}

	@Override
	public void onMouseUp(MouseClickEvent event) {
	}

	@Override
	public void render(MapCanvas canvas, int currentX, int currentY) {
		canvas.drawText(currentX, currentY, Constants.Font, text.toString());
	}

	private static final MapString text = new MapString(MapColour.LightGreen
			+ "<- ").concat(MapColour.Blue + "back");
}
