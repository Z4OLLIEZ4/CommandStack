package com.olliez4.commandstack.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.olliez4.commandstack.main.CommandStack;
import com.olliez4.interface4.util.VersionUtils;

public class CommandStackPlayerJoinEvent implements Listener {

	private CommandStack main;

	public CommandStackPlayerJoinEvent(CommandStack main) {
		this.main = main;
	}

	@EventHandler
	public void attemptJoin(PlayerJoinEvent e) {
		if (main.getConfig().getBoolean("Settings.Updater.Alert-OP")) {
			if (e.getPlayer().isOp()) {
				if (!VersionUtils.isLatest(main, 102817)) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
						public void run() {
							e.getPlayer()
									.sendMessage(main.getPrefix() + "CommandStack v"
											+ VersionUtils.getLatest(main, 102817) + " is available (Current: v"
											+ main.getDescription().getVersion() + ")");
							e.getPlayer().sendMessage(
									main.getPrefix() + "https://www.spigotmc.org/resources/commandstack.102817/");
						}
					}, 10);
				}
			}
		}
	}

}
