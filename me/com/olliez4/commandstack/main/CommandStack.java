package com.olliez4.commandstack.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.olliez4.commandstack.commands.CommandStackCommand;
import com.olliez4.commandstack.commands.ConstructTabCompleter;
import com.olliez4.commandstack.events.CommandStackPlayerInteractEvent;
import com.olliez4.commandstack.events.CommandStackPlayerJoinEvent;
import com.olliez4.commandstack.metrics.Metrics;
import com.olliez4.interface4.util.PluginUtils;
import com.olliez4.interface4.util.VersionUtils;

public class CommandStack extends JavaPlugin {

	public void onEnable() {
		log("Thanks for using CommandStack by OLLIEZ4");
		saveDefaultConfig(); // Generate configuration
		getCommand("commandstack").setExecutor(new CommandStackCommand(this));
		getCommand("commandstack").setTabCompleter(new ConstructTabCompleter(this));
		getServer().getPluginManager().registerEvents(new CommandStackPlayerInteractEvent(this), this);
		getServer().getPluginManager().registerEvents(new CommandStackPlayerJoinEvent(this), this);
		new Metrics(this, 15526); // Enable metrics

		if (getConfig().getBoolean("Settings.Updater.Auto-Update")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					try {
						if (!VersionUtils.isLatest(PluginUtils.getMain("CommandStack"), 102817))
							if (VersionUtils.updatePlugin(PluginUtils.getMain("Interface4"), 102817)) {
								if (getConfig().getBoolean("Settings.Updater.Auto-Restart")) {
									log("&cCommandStack has auto-updated. Restarting server to see changes.");
									Bukkit.shutdown();
								} else {
									log("&cCommandStack has auto-updated. Please restart server to see changes.");
								}
							}
					} catch (Exception ex) {
						// In event of any critical issues, prevent shutdown
					}
				}
			}, 40);
		}
	}

	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', "&3CommandStack &8&l> &7");
	}

	// Logs to console in colour
	public void log(String str) {
		getServer().getConsoleSender()
				.sendMessage(ChatColor.translateAlternateColorCodes('&', "[CommandStack] " + str));
	}

}
