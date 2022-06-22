package com.olliez4.commandstack.utils;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.olliez4.commandstack.main.CommandStack;
import com.olliez4.interface4.util.json.JSON;
import com.olliez4.interface4.util.json.components.JPromptComponent;
import com.olliez4.interface4.util.json.components.JTextComponent;

public class ItemUtils {

	private CommandStack main;

	public ItemUtils(CommandStack main) {
		this.main = main;
	}

	private void setNBT(ItemStack is, String nbt) {
		NamespacedKey key = new NamespacedKey(main, "commandstack");
		ItemMeta itemMeta = is.getItemMeta();
		itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, nbt);
		is.setItemMeta(itemMeta);
	}

	public void unsetNBT(ItemStack is) {
		NamespacedKey key = new NamespacedKey(main, "commandstack");
		ItemMeta itemMeta = is.getItemMeta();
		itemMeta.getPersistentDataContainer().remove(key);
		is.setItemMeta(itemMeta);
	}

	private String getNBT(ItemStack is) {
		try {
			NamespacedKey key = new NamespacedKey(main, "commandstack");
			ItemMeta itemMeta = is.getItemMeta();
			PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			if (container.has(key, PersistentDataType.STRING)) {
				return container.get(key, PersistentDataType.STRING);
			}
		} catch (Exception ex) {
			return "";
		}
		return "";
	}

	public void addCommand(Player p, ItemStack is, String[] cmd) {
		String toAdd = "";
		if (cmd.length > 1) {
			String currentNBT = getNBT(is);
			for (int i = 1; i < cmd.length; i++) {
				toAdd = toAdd + cmd[i] + " ";
			}
			toAdd = toAdd.trim();
			toAdd = toAdd.replaceAll(" ", "%s");
			if (currentNBT.equals("")) {
				setNBT(is, toAdd);
			} else {
				setNBT(is, currentNBT + "@@" + toAdd);
			}
			p.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&', main.getConfig()
					.getString("Lang.Added-Command").replaceAll("%cmd%", "\"/" + toAdd.replaceAll("%s", " ") + "\"")));
		} else {
			p.sendMessage(main.getPrefix() + "Usage: " + main.getCommand("cstack").getUsage());
		}
	}

	public void removeCommand(Player p, ItemStack is, String[] cmd) {
		String currentNBT = getNBT(is);
		if (currentNBT.equals("")) {
			p.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
					main.getConfig().getString("Lang.Does-Not-Have-Command")));
		} else {
			String command = "";
			for (int i = 1; i < cmd.length; i++) {
				if (command.equals("")) {
					command = cmd[i];
				} else {
					command = command + "%s" + cmd[i];
				}
			}
			boolean all = command.equalsIgnoreCase("all");
			String[] split = currentNBT.split("@@");
			String toReplace = "";
			boolean removed = false;
			for (String s : split) {
				if (all) {
					removed = true;
				} else {
					if (!s.equalsIgnoreCase(command)) {
						if (toReplace.equals("")) {
							toReplace = s;
						} else {
							toReplace = toReplace + "@@" + s;
						}
					} else {
						removed = true;
					}
				}
			}
			if (removed) {
				if (toReplace.trim().equals("") | all) {
					unsetNBT(is);
				} else {
					setNBT(is, toReplace);
				}
				if (all) {
					p.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
							main.getConfig().getString("Lang.Removed-All-Commands")));
				} else {
					p.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
							main.getConfig().getString("Lang.Removed-Command").replaceAll("%cmd%",
									"\"/" + command.replaceAll("%s", " ") + "\"")));
				}
			} else {
				p.sendMessage(main.getPrefix() + ChatColor.translateAlternateColorCodes('&',
						main.getConfig().getString("Lang.Does-Not-Have-Command")));
			}
		}
	}

	public ArrayList<String> getCommands(ItemStack stack) {
		if (stack == null)
			return null;
		ArrayList<String> commands = new ArrayList<String>();
		String currentNBT = getNBT(stack);
		if (currentNBT.equals("")) {
			return null;
		} else {
			String[] splitCommands = currentNBT.split("@@");
			for (String s : splitCommands) {
				s = s.replaceAll("%s", " ");
				commands.add(s);
			}
		}
		return commands;
	}

	public void listCommands(Player p, ItemStack is) {
		ArrayList<String> commands = getCommands(is);
		if (commands != null) {
			for (String cmd : commands) {
				JTextComponent text = new JTextComponent(main.getPrefix() + "&8-&7 \"/" + cmd + "\" ", null);
				JPromptComponent prompt = new JPromptComponent("&r[&4&l✗&r]", "Click to remove",
						"/commandstack remove " + cmd);
				JSON.send(p, text, prompt);
			}
		} else {
			p.sendMessage(main.getPrefix()
					+ ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Lang.No-Commands")));
		}
	}

}
