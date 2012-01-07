package com.fullwall.maps.os;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import com.fullwall.maps.interfaces.Loadable;
import com.fullwall.maps.interfaces.Saveable;
import com.fullwall.maps.interfaces.StateHolder;
import com.fullwall.maps.storage.NBTStorage;
import com.fullwall.maps.storage.jnbt.Tag;
import com.google.common.collect.Sets;

class NBTStateHolder implements StateHolder<Map<String, Tag>> {
	private final Set<Saveable> autosavers = Sets.newHashSet();
	private final Set<Loadable> loaders = Sets.newHashSet();
	private final OSSettingsStorage store;

	NBTStateHolder(Player player) {
		store = new OSSettingsStorage(new File("plugins/MapOS/data/players/"
				+ player.getName().toLowerCase() + ".dat"));
	}

	@Override
	public void addLoader(Loadable loader) {
		loaders.add(loader);
	}

	@Override
	public void addSaver(Saveable saver) {
		autosavers.add(saver);
	}

	@Override
	public Map<String, Tag> getGlobalStates() {
		return store.getEditable();
	}

	@Override
	public void load() {
		for (Loadable loader : loaders) {
			load(loader);
		}
	}

	@Override
	public void load(Loadable loader) {
		boolean existed = store.keyExists(loader.getRootName());
		loader.load(store.getKey(loader.getRootName()), existed);
	}

	@Override
	public void save() {
		for (Saveable saver : autosavers) {
			store(saver);
		}
		store.save();
	}

	@Override
	public void store(Saveable saver) {
		saver.save(store.getKey("").getRelative(saver.getRootName()));
	}

	private class OSSettingsStorage extends NBTStorage {
		private OSSettingsStorage(File file) {
			super(file, "os");
		}

		public boolean keyExists(String rootName) {
			return super.values.containsKey(rootName);
		}

		private Map<String, Tag> getEditable() {
			return super.values;
		}
	}
}