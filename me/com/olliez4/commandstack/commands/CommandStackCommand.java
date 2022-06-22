package com.olliez4.commandstack.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.olliez4.commandstack.main.CommandStack;
import com.olliez4.commandstack.utils.ItemUtils;

public class CommandStackCommand implements CommandExecutor {

	private CommandStack main;
	private ItemUtils itemUtils;

	public CommandStackCommand(CommandStack commandStack) {
		this.main = commandStack;
		this.itemUtils = new ItemUtils(commandStack);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.getInventory().getItemInMainHand() != null) {
					if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
						if (args[0].equalsIgnoreCase("add")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.Add"))) {
								itemUtils.addCommand(p, p.getInventory().getItemInMainHand(), args);
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else if (args[0].equalsIgnoreCase("remove")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.Remove"))) {
								itemUtils.removeCommand(p, p.getInventory().getItemInMainHand(), args);
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else if (args[0].equalsIgnoreCase("version")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.Remove"))) {
								sender.sendMessage(main.getPrefix() + "Running v" + main.getDescription().getVersion());
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else if (args[0].equalsIgnoreCase("view")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.View"))) {
								itemUtils.listCommands(p, p.getInventory().getItemInMainHand());
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else if (args[0].equalsIgnoreCase("credits")) {
							sender.sendMessage(main.getPrefix() + "CommandStack was created by OLLIEZ4");
						} else if (args[0].equalsIgnoreCase("reload")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.Reload"))) {
								main.reloadConfig();
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.Reloaded")));
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else {
							for (String usage : main.getConfig().getStringList("Lang.Usage"))
								sender.sendMessage(
										main.getPrefix() + ChatColor.translateAlternateColorCodes('&', usage));
						}
					} else {
						if (args[0].equalsIgnoreCase("credits")) {
							sender.sendMessage(main.getPrefix() + "CommandStack was created by OLLIEZ4");
						} else if (args[0].equalsIgnoreCase("version")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.Remove"))) {
								sender.sendMessage(main.getPrefix() + "Running v" + main.getDescription().getVersion());
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else if (args[0].equalsIgnoreCase("reload")) {
							if (p.hasPermission(main.getConfig().getString("Permissions.Reload"))) {
								main.reloadConfig();
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.Reloaded")));
							} else {
								sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
										main.getConfig().getString("Lang.No-Permissions")));
							}
						} else {
							sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
									main.getConfig().getString("Lang.Not-Holding-Item")));
						}
					}
				} else {
					if (args[0].equalsIgnoreCase("credits")) {
						sender.sendMessage(main.getPrefix() + "CommandStack was created by OLLIEZ4");
					} else if (args[0].equalsIgnoreCase("version")) {
						if (p.hasPermission(main.getConfig().getString("Permissions.Remove"))) {
							sender.sendMessage(main.getPrefix() + "Running v" + main.getDescription().getVersion());
						} else {
							sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
									main.getConfig().getString("Lang.No-Permissions")));
						}
					} else if (args[0].equalsIgnoreCase("reload")) {
						if (p.hasPermission(main.getConfig().getString("Permissions.Reload"))) {
							main.reloadConfig();
							sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
									main.getConfig().getString("Lang.Reloaded")));
						} else {
							sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
									main.getConfig().getString("Lang.No-Permissions")));
						}
					} else {
						sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
								main.getConfig().getString("Lang.Not-Holding-Item")));
					}
				}
			} else {
				sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
						main.getConfig().getString("Lang.Player-Only-Command")));
			}

		} else {
			for (String usage : main.getConfig().getStringList("Lang.Usage"))
				sender.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&', usage));

		}
		return true;
	}

}
