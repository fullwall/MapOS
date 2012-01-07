package com.fullwall.maps.menus;

import java.util.Collection;

import com.fullwall.maps.attachments.AttachmentDisposer;
import com.fullwall.maps.attachments.RendererDisposer;
import com.fullwall.maps.interfaces.DelayedRenderer;
import com.fullwall.maps.interfaces.Screen;

public interface Menu extends RendererDisposer, AttachmentDisposer,
		DelayedRenderer {
	void attach(Screen screen);

	Collection<MenuItem> getItems();

	MenuRenderer getRenderer();

	MenuStyle getStyle();

	MenuTitle getTitle();
}