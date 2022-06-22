package com.olliez4.commandstack.events;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.olliez4.commandstack.main.CommandStack;
import com.olliez4.commandstack.utils.ItemUtils;

public class CommandStackPlayerInteractEvent implements Listener {

	private CommandStack main;
	private ItemUtils itemUtils;

	public CommandStackPlayerInteractEvent(CommandStack main) {
		this.main = main;
		this.itemUtils = new ItemUtils(main);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!e.getPlayer().hasPermission(main.getConfig().getString("Permissions.Use")))
			return;
		ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
		ArrayList<String> commands = itemUtils.getCommands(hand);
		if (commands != null) {
			for (String cmd : commands)
				e.getPlayer().performCommand(cmd);
			if (main.getConfig().getBoolean("Settings.Prevent-Placement"))
				e.setCancelled(true);
		}
	}

}
