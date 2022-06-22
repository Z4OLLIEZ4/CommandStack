package com.olliez4.commandstack.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.olliez4.commandstack.main.CommandStack;

public class ConstructTabCompleter implements TabCompleter {

	private CommandStack main;

	public ConstructTabCompleter(CommandStack commandStack) {
		this.main = commandStack;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String arg2, String[] args) {
		if (command.getName().equalsIgnoreCase("commandstack"))
			if (args.length == 1) {
				List<String> finalTabCompleter = new ArrayList<>();
				String[] arg = main.getCommand("cstack").getUsage().replaceAll("/cstack <", "").replace(">", "").split(" - ");
				for (String s : arg)
					finalTabCompleter.add(s);
				return finalTabCompleter.stream().filter(a -> startsWith(a, args[0])).collect(Collectors.toList());
			}
		return null;
	}

	public boolean startsWith(String a, String arg) {
		return a.regionMatches(true, 0, arg, 0, arg.length());
	}

}
