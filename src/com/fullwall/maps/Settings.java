package com.fullwall.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fullwall.maps.storage.ConfigurationStorage;
import com.fullwall.maps.storage.DataSource;
import com.fullwall.maps.storage.Node;
import com.fullwall.maps.utils.Messaging;

public class Settings {
	public enum SettingsType {
		/**
		 * config.yml
		 */
		GENERAL(new ConfigurationStorage("plugins/MapPlugin/config.yml"));
		private final DataSource storage;

		SettingsType(DataSource storage) {
			this.storage = storage;
		}

		public DataSource getStorage() {
			return storage;
		}
	}

	private static List<Node> nodes = new ArrayList<Node>();

	private static Map<String, Object> loadedNodes = new HashMap<String, Object>();

	public static boolean getBoolean(String name) {
		try {
			return (Boolean) loadedNodes.get(name);
		} catch (NullPointerException e) {
			Messaging.log("Report this error ASAP.");
			e.printStackTrace();
			return false;
		}
	}

	public static double getDouble(String name) {
		try {
			if (loadedNodes.get(name) instanceof Float) {
				return (Float) loadedNodes.get(name);
			} else if (loadedNodes.get(name) instanceof Double) {
				return (Double) loadedNodes.get(name);
			} else {
				return (Integer) loadedNodes.get(name);
			}
		} catch (NullPointerException e) {
			Messaging.log("Report this error ASAP.");
			e.printStackTrace();
			return 0;
		}
	}

	public static int getInt(String name) {
		try {
			return (Integer) loadedNodes.get(name);
		} catch (NullPointerException e) {
			Messaging.log("Report this error ASAP.");
			e.printStackTrace();
			return 0;
		}
	}

	public static String getString(String name) {
		try {
			return (String) loadedNodes.get(name);
		} catch (NullPointerException e) {
			Messaging.log("Report this error ASAP.");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Sets up miscellaneous variables, mostly reading from property files.
	 */
	public static void setupVariables() {
		DataSource local;
		for (Node node : nodes) {
			local = node.getType().getStorage();
			if (!local.getKey(node.getPath()).keyExists("")) {
				Messaging
						.log("Writing default setting " + node.getPath() + ".");
				local.getKey(node.getPath()).setRaw("", node.getValue());
			} else {
				node.set(local.getKey(node.getPath()).getRaw(""));
			}
			loadedNodes.put(node.getName(), node.getValue());
			local.save();
		}
	}

	static {
	}
}