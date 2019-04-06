package com.MegaCraft.Nations.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import com.MegaCraft.Nations.Nations;

public class NationsConfig {

	static Nations plugin;

	public NationsConfig(Nations plugin) {
		NationsConfig.plugin = plugin;
		loadConfigCore();
	}

	private void loadConfigCore() {
		FileConfiguration config;
		config = Nations.plugin.getConfig();

		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
}
