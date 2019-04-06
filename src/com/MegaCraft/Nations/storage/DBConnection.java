package com.MegaCraft.Nations.storage;

import org.bukkit.Bukkit;

import com.MegaCraft.Nations.Nations;

public class DBConnection {

	public static Database sql;

	public static boolean isOpen = false;

	public static void init() {
		sql = new SQLite(Nations.plugin.getLogger(), "Establishing SQLite Connection.", "Nations.db",
				Nations.plugin.getDataFolder().getAbsolutePath());
		if (((SQLite) sql).open() == null) {
			Nations.plugin.getLogger().severe("Disabling due to database error");
			Bukkit.getPluginManager().disablePlugin(Nations.plugin);
			return;
		}

		isOpen = true;
		if (!sql.tableExists("nations")) {
			Nations.log.info("Creating nations table.");
			String query = "CREATE TABLE `nations` (" 
			+ "`uuid` TEXT(50) PRIMARY KEY," 
			+ "`name` TEXT(255),"
			+ "`founder` TEXT(255),"
			+ "`description` TEXT(255)"
			+ ");";
			sql.modifyQuery(query);
		}
		
		if (!sql.tableExists("members")) {
			Nations.log.info("Creating members table.");
			String query = "CREATE TABLE `members` (" 
			+ "`nationuuid` TEXT(50)," 
			+ "`playeruuid` TEXT(50) PRIMARY KEY,"
			+ "`rank` TEXT(255)"
			+ ");";
			sql.modifyQuery(query);
		}
	}

	public static boolean isOpen() {
		return isOpen;
	}
}
