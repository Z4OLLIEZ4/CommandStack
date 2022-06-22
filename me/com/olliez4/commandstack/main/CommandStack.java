package com.olliez4.commandstack.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.olliez4.commandstack.commands.CommandStackCommand;
import com.olliez4.commandstack.commands.ConstructTabCompleter;
import com.olliez4.commandstack.events.CommandStackPlayerInteractEvent;
import com.olliez4.commandstack.metrics.Metrics;

public class CommandStack extends JavaPlugin {

	public void onEnable() {
		log("Thanks for using CommandStack by OLLIEZ4");
		saveDefaultConfig(); // Generate configuration
		getCommand("commandstack").setExecutor(new CommandStackCommand(this));
		getCommand("commandstack").setTabCompleter(new ConstructTabCompleter(this));
		getServer().getPluginManager().registerEvents(new CommandStackPlayerInteractEvent(this), this);
		new Metrics(this, 15526); // Enable metrics
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
