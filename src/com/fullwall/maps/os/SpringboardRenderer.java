package com.fullwall.maps.os;

import java.util.List;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.applications.ApplicationIcon;
import com.fullwall.maps.applications.ApplicationIcon.IconScale;
import com.fullwall.maps.applications.ApplicationProvider;
import com.fullwall.maps.applications.CanvasRenderer;
import com.fullwall.maps.attachments.Clickable;
import com.fullwall.maps.attachments.MouseClickEvent;
import com.fullwall.maps.attachments.MouseMoveEvent;
import com.fullwall.maps.attachments.ScreenAttachment;
import com.fullwall.maps.attachments.builtin.AbstractBoxButton;
import com.fullwall.maps.interfaces.ApplicationController;
import com.fullwall.maps.interfaces.InstalledApplications;
import com.fullwall.maps.interfaces.Loadable;
import com.fullwall.maps.interfaces.Saveable;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.shapes.Rectangle;
import com.fullwall.maps.storage.NBTMemoryStorage.NBTKey;
import com.google.common.collect.Lists;

public class SpringboardRenderer implements CanvasRenderer, Saveable, Loadable {
	private byte currentPage = 1;
	private boolean rendered = false;
	private final ApplicationController applicationController;
	private final InstalledApplications installed;
	private final List<ScreenAttachment> buttons = Lists.newArrayList();

	public SpringboardRenderer(ApplicationController application,
			InstalledApplications installed) {
		this.applicationController = application;
		this.installed = installed;
	}

	@Override
	public boolean clearPixelsOnRemove() {
		return true;
	}

	@Override
	public RenderPriority getPriority() {
		return RenderPriority.Normal;
	}

	@Override
	public String getRootName() {
		return "springboard";
	}

	@Override
	public boolean isRendering() {
		return !rendered;
	}

	@Override
	public void load(NBTKey settings, boolean empty) {
		if (empty) {
			return;
		}
		currentPage = (byte) settings.getInt("page");
	}

	@Override
	public void render(Screen screen, MapCanvas canvas) {
		if (!rendered) {
			screen.getAttachments().removeAll(buttons);
			int end = currentPage * (ROWS * COLS), start = end - (ROWS * COLS);
			ApplicationIcon[] icons = new ApplicationIcon[ROWS * COLS];
			for (int i = start, idx = 0; i < end && installed.size() > i; ++i, ++idx) {
				ApplicationProvider provider = installed.getInstalled(i);
				icons[idx] = provider != null ? provider
						.getIcon(IconScale.Layout) : Images.MissingApp;
			}
			int x = HORIZONTAL_PADDING, y = VERTICAL_PADDING, idx = 0;
			for (int i = 0; i < ROWS; ++i) {
				for (int j = 0; j < COLS; ++j) {
					if (icons[idx] == null)
						break;
					icons[idx].render(x, y, canvas);
					Clickable button = new ApplicationSwitcherButton(x, y,
							installed.getInstalled(icons[idx].getName()));
					screen.getAttachments().attach(button);
					buttons.add(button);
					x += IconScale.Layout.getWidth() + INNER_SPACING;
					++idx;
				}
				x = HORIZONTAL_PADDING;
				y += IconScale.Layout.getHeight() + APP_SPACING;
			}
			rendered = true;
		}
	}

	@Override
	public void save(NBTKey root) {
		root.setInt("page", currentPage);
	}

	@Override
	public void setDirty() {
		this.rendered = false;
	}

	private class ApplicationSwitcherButton extends AbstractBoxButton {
		private final ApplicationProvider provider;

		private ApplicationSwitcherButton(int x, int y,
				ApplicationProvider provider) {
			super(new Rectangle(x, y, IconScale.Layout.getWidth(),
					IconScale.Layout.getHeight()));
			this.provider = provider;
		}

		@Override
		public void onMouseDown(MouseClickEvent event) {
			applicationController.switchApplication(provider);
		}

		@Override
		public void onMouseMove(MouseMoveEvent event) {
		}

		@Override
		public void onMouseUp(MouseClickEvent event) {
		}
	}

	private static final byte ROWS = 3, COLS = 2, HORIZONTAL_PADDING = 22,
			VERTICAL_PADDING = 5, INNER_SPACING = 28, APP_SPACING = 5;
}