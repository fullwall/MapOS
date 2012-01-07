package com.fullwall.maps.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fullwall.maps.storage.jnbt.ByteArrayTag;
import com.fullwall.maps.storage.jnbt.ByteTag;
import com.fullwall.maps.storage.jnbt.CompoundTag;
import com.fullwall.maps.storage.jnbt.DoubleTag;
import com.fullwall.maps.storage.jnbt.IntTag;
import com.fullwall.maps.storage.jnbt.LongTag;
import com.fullwall.maps.storage.jnbt.StringTag;
import com.fullwall.maps.storage.jnbt.Tag;
import com.fullwall.maps.utils.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class NBTMemoryStorage implements DataSource {
	protected final Map<String, Tag> values;

	protected NBTMemoryStorage() {
		this.values = Maps.newHashMap();
	}

	public NBTMemoryStorage(Map<String, Tag> settings) {
		this.values = settings != null ? settings : new HashMap<String, Tag>();
	}

	@Override
	public void load() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void save() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NBTKey getKey(String root) {
		return new NBTKey(values);
	}

	/*private Map<String, Tag> traverse(Map<String, Tag> map, String path) {
		if (path.isEmpty())
			return map;
		int idx = path.indexOf('.');
		Tag tag = map.get(path.substring(0, idx));
		if (!(tag instanceof CompoundTag)) {
			return Maps.newHashMap();
		}
		return traverse(((CompoundTag) tag).getValue(),
				path.substring(idx + 1, path.length()));
	}*/

	public class NBTKey extends AbstractDataKey {
		private Tag safeGetTag(String key) {
			return values.containsKey(key) ? values.get(key) : new Tag("") {
				@Override
				public Object getValue() {
					return null;
				}
			};
		}

		private Map<String, Tag> values = Maps.newHashMap();

		public NBTKey(Map<String, Tag> value) {
			this.values = value;
		}

		public byte[] getByteArray(String key) {
			return ((ByteArrayTag) safeGetTag(key)).getValue();
		}

		public byte[] getByteArray(String key, byte[] def) {
			if (keyExists(key))
				return getByteArray(key);
			setByteArray(key, def);
			return def;
		}

		public void setByteArray(String key, byte[] array) {
			values.put(key, new ByteArrayTag("", array));
		}

		@Override
		public void copy(String to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean getBoolean(String keyExt) {
			if (!values.containsKey(keyExt))
				return false;
			Tag tag = values.get(keyExt);
			return tag instanceof ByteTag ? ((ByteTag) tag).getValue() == 1
					: false;
		}

		@Override
		public double getDouble(String keyExt) {
			if (!values.containsKey(keyExt))
				return 0D;
			Tag tag = values.get(keyExt);
			return tag instanceof DoubleTag ? ((DoubleTag) tag).getValue() : 0D;
		}

		@Override
		public int getInt(String keyExt) {
			if (!values.containsKey(keyExt))
				return 0;
			Tag tag = values.get(keyExt);
			return tag instanceof IntTag ? ((IntTag) tag).getValue() : 0;
		}

		@Override
		public List<DataKey> getIntegerSubKeys() {
			List<DataKey> subs = Lists.newArrayList();
			for (Entry<String, Tag> entry : values.entrySet()) {
				if (Strings.isNumber(entry.getKey())
						&& entry.getValue() instanceof CompoundTag) {
					subs.add(new NBTKey(((CompoundTag) entry.getValue())
							.getValue()));
				}
			}
			return subs;
		}

		@Override
		public long getLong(String keyExt) {
			if (!values.containsKey(keyExt))
				return 0;
			Tag tag = values.get(keyExt);
			return tag instanceof LongTag ? ((LongTag) tag).getValue() : 0;
		}

		@Override
		public Object getRaw(String keyExt) {
			throw new UnsupportedOperationException();
		}

		@Override
		public NBTKey getRelative(String relative) {
			Tag tag = values.get(relative);
			if (tag != null && tag instanceof CompoundTag)
				return new NBTKey(((CompoundTag) tag).getValue());
			Map<String, Tag> value = Maps.newHashMap();
			values.put(relative, new CompoundTag(relative, value));
			return new NBTKey(value);
		}

		@Override
		public String getString(String keyExt) {
			if (!values.containsKey(keyExt))
				return "";
			Tag tag = values.get(keyExt);
			return tag instanceof StringTag ? ((StringTag) tag).getValue() : "";
		}

		@Override
		public Iterable<DataKey> getSubKeys() {
			List<DataKey> list = Lists.newArrayList();
			for (Tag tag : values.values()) {
				if (tag instanceof CompoundTag)
					list.add(new NBTKey(((CompoundTag) tag).getValue()));
			}
			return list;
		}

		@Override
		public boolean keyExists(String keyExt) {
			return values.containsKey(keyExt);
		}

		@Override
		public String name() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void removeKey(String keyExt) {
			values.remove(keyExt);
		}

		@Override
		public void setBoolean(String keyExt, boolean value) {
			values.put(keyExt, new ByteTag(keyExt, (byte) (value ? 1 : 0)));
		}

		@Override
		public void setDouble(String keyExt, double value) {
			values.put(keyExt, new DoubleTag(keyExt, value));
		}

		@Override
		public void setInt(String keyExt, int value) {
			values.put(keyExt, new IntTag(keyExt, value));
		}

		@Override
		public void setLong(String keyExt, long value) {
			values.put(keyExt, new LongTag(keyExt, value));
		}

		@Override
		public void setRaw(String path, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setString(String keyExt, String value) {
			values.put(keyExt, new StringTag(keyExt, value));
		}

		@Override
		boolean valueExists(String key) {
			return values.containsKey(key);
		}
	}
}
