package com.fullwall.maps.menus;

import java.util.Collection;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.interfaces.Screen;

class SimpleMenuRenderer implements MenuRenderer {
	private final MenuTitle title;
	private final Collection<MenuItem> items;
	private final MenuStyle style;
	private boolean rendered = true;
	private int indentLevel;

	SimpleMenuRenderer(MenuStyle style, MenuTitle title,
			Collection<MenuItem> items) {
		this.style = style;
		this.items = items;
		this.title = title;
	}

	@Override
	public void beginRendering() {
		rendered = false;
	}

	@Override
	public boolean clearPixelsOnRemove() {
		return true;
	}

	@Override
	public int getIndent() {
		return indentLevel;
	}

	@Override
	public RenderPriority getPriority() {
		return RenderPriority.Normal;
	}

	@Override
	public boolean isRendering() {
		return !rendered;
	}

	@Override
	public void render(Screen screen, MapCanvas canvas) {
		if (!rendered) {
			int x = title.padding().left(), y = title.padding().upper();
			canvas.drawText(x, y, title.getTitle().getFont(), title.getTitle()
					.toString());
			y = style.defaultPadding().upper();
			for (MenuItem item : items) {
				x = style.defaultPadding().left() + indentLevel;

				style.renderIndentation(canvas, x, y, indentLevel);
				item.render(canvas, x, y);

				y += style.getItemSpacing() + item.getHeight();
			}
			rendered = true;
		}
	}

	@Override
	public void setDirty() {
		this.rendered = false;
	}

	@Override
	public void setIndent(int indent) {
		this.indentLevel = indent;
	}
}
