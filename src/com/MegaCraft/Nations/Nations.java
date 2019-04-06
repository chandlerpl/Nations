package com.MegaCraft.Nations;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.MegaCraft.Nations.commands.NationsCommand;
import com.MegaCraft.Nations.configuration.NationsConfig;
import com.MegaCraft.Nations.instances.Nation;
import com.MegaCraft.Nations.listener.NationsListener;
import com.MegaCraft.Nations.storage.DBConnection;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Nations extends JavaPlugin {
	public static Nations plugin;
	public static Logger log;
	public static String dev;
	public static String version;
	
	static boolean debug = false;

	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	@Override
	public void onEnable() {
		if (!isJava8orHigher()) {
			getLogger().info("MegaBending requires Java 8+! Disabling Nations...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		plugin = this;
		Nations.log = this.getLogger();

		if (!isSpigot()) {
			log.info("Bukkit detected, Nations will not function properly.");
		}

		DBConnection.init();
		if (DBConnection.isOpen() == false) {
			// Message is logged by DBConnection
			return;
		}
		
		if(NationsMethods.loadNations() && NationsMethods.loadNationsMembers()) {
			getLogger().info("All Nations have successfully loaded.");
		} else {
			getLogger().info("A problem has occurred loading the Nations. Disabling Nations...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		if (setupPermissions() && setupChat() && setupEconomy()) {
			getLogger().info("Nations is now hooked with Vault");
		} else {
			getLogger().info("Nations failed to hook with Vault");
		}
		
		version = this.getDescription().getVersion();

		new NationsCommand(this);
		new NationsConfig(this);
		this.getServer().getPluginManager().registerEvents((Listener) new NationsListener(this), (Plugin) this);
		Nations.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Nations.plugin, new NationsManager(Nations.plugin), 0, 1200);
	}

	public static boolean isSpigot() {
		return plugin.getServer().getVersion().toLowerCase().contains("spigot");
	}

	private boolean isJava8orHigher() {
		return Integer.valueOf(System.getProperty("java.version").substring(2, 3)) >= 8;
	}

	public void onDisable() {
		for(Nation nation : Nation.nationInstances.values()) {
			NationsMethods.saveNation(nation);
			NationsMethods.saveNationMembers(nation);
		}
		if (DBConnection.isOpen != false) {
			DBConnection.sql.close();
		}

		NationsCommand.disconnect();
	}
}
