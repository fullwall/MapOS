package com.fullwall.maps.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.fullwall.maps.storage.jnbt.CompoundTag;
import com.fullwall.maps.storage.jnbt.NBTInputStream;
import com.fullwall.maps.storage.jnbt.NBTOutputStream;
import com.fullwall.maps.storage.jnbt.Tag;
import com.fullwall.maps.utils.Messaging;
import com.google.common.io.Closeables;

public class NBTStorage extends NBTMemoryStorage {
	private final File file;
	private final String name;

	public NBTStorage(File file) {
		this(file, "root");
	}

	public NBTStorage(File file, String tagName) {
		name = tagName;
		this.file = file;
		if (!file.exists()) {
			if (file.getParentFile() != null)
				file.getParentFile().mkdirs();
			save();
			return;
		}
		load();
	}

	@Override
	public void load() {
		NBTInputStream stream = null;
		try {
			stream = new NBTInputStream(new GZIPInputStream(
					new FileInputStream(file)));
			Tag tag = stream.readTag();
			if (tag == null || !(tag instanceof CompoundTag)) {
				Messaging.log("Invalid NBT structure for: " + file.getName()
						+ ".");
				return;
			} else {
				values.clear();
				values.putAll(((CompoundTag) tag).getValue());
			}
		} catch (IOException ex) {
			Messaging.log("Unable to read NBT from " + file.getPath()
					+ ", error: " + ex.getMessage() + ".");
			ex.printStackTrace();
		} finally {
			Closeables.closeQuietly(stream);
		}
	}

	@Override
	public void save() {
		NBTOutputStream stream = null;
		try {
			stream = new NBTOutputStream(new FileOutputStream(file));
			stream.writeTag(new CompoundTag(name, values));
		} catch (IOException ex) {
			Messaging.log("Unable to write NBT to: " + file.getName()
					+ ", error: " + ex.getMessage() + ".");
		} finally {
			Closeables.closeQuietly(stream);
		}
	}
}
