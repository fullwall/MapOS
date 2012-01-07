package com.fullwall.maps.applications.builtin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.fullwall.maps.applications.AbstractApplication;
import com.fullwall.maps.applications.Application;
import com.fullwall.maps.applications.ApplicationFactory;
import com.fullwall.maps.applications.ApplicationIcon;
import com.fullwall.maps.applications.ApplicationIcon.IconScale;
import com.fullwall.maps.applications.ApplicationProvider;
import com.fullwall.maps.applications.Capability;
import com.fullwall.maps.interfaces.ApplicationController.InterruptReason;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.menus.Menu;
import com.fullwall.maps.menus.MenuItem;
import com.fullwall.maps.menus.SimpleMenu;
import com.fullwall.maps.os.Images;
import com.fullwall.maps.os.OperatingSystem;
import com.fullwall.maps.storage.NBTMemoryStorage.NBTKey;
import com.fullwall.maps.storage.NBTStorage;
import com.fullwall.maps.storage.jnbt.Tag;
import com.google.common.collect.Lists;

public class SettingsApp {
	private SettingsApp() {
	}

	private static class App extends AbstractApplication {
		private final Screen screen;
		private final Menu menu = new SimpleMenu(new ArrayList<MenuItem>());

		private App(Screen screen) {
			this.screen = screen;
			menu.attach(screen);
		}

		@Override
		public void begin() {
		}

		@Override
		public String getAppName() {
			return "Settings";
		}

		@Override
		public String getRootName() {
			return "settings";
		}

		@Override
		public void interrupt(InterruptReason reason) {
		}

		@Override
		public void save(NBTKey root) {
		}
	}

	private static class Provider implements ApplicationFactory {
		@Override
		public Application create(OperatingSystem os,
				Map<String, Tag> localSettings) {
			return new App(os.getScreen());
		}

		@Override
		public String getName() {
			return "Settings";
		}

		@Override
		public void loadGlobalSettings(NBTStorage storage) {
		}

		@Override
		public Set<Capability> getCapabilities() {
			return Capability.NONE;
		}

		@Override
		public Collection<String> getCommands() {
			return Lists.newArrayList();
		}

		@Override
		public ApplicationIcon getIcon(IconScale scale) {
			return Images.MissingApp;
		}
	}

	public static final ApplicationProvider Provider = new ApplicationProvider(
			new Provider());
}
