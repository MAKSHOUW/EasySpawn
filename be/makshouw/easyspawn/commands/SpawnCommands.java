package be.makshouw.easyspawn.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import be.makshouw.easyspawn.EasySpawn;

public class SpawnCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(EasySpawn.getMainClass().getMessage("prefix") + EasySpawn.getMainClass().getMessage("sender_is_not_player"));
			return false;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("spawn")) {
			
			if (args.length == 0) {
				
				Location spawnLocation = EasySpawn.getMainClass().StringToLocation(EasySpawn.getMainClass().getMessage("spawn_loc"));
				player.teleport(spawnLocation);
				player.sendMessage(EasySpawn.getMainClass().getMessage("prefix") + "" + EasySpawn.getMainClass().getMessage("teleport_accepted"));
				
			} else if(args.length == 1) {
				
				if(args[0].toString().equalsIgnoreCase("reload")) {
					
					if (player.hasPermission(EasySpawn.getMainClass().getMessage("reload_config_permission"))) {
						
						EasySpawn.getMainClass().rlConfig();
						player.sendMessage(EasySpawn.getMainClass().getMessage("config_reloaded_success"));
						
					} else {
						player.sendMessage(EasySpawn.getMainClass().getMessage("prefix") + EasySpawn.getMainClass().getMessage("no_permission"));
					}
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (player.hasPermission(EasySpawn.getMainClass().getMessage("setspawn_permission"))) {

				EasySpawn.getMainClass().getConfig().set("location.loc", EasySpawn.getMainClass().RealLocationToOneLine(player.getLocation()));
				EasySpawn.getMainClass().saveConfig();
				EasySpawn.getMainClass().rlConfig();

				player.sendMessage(EasySpawn.getMainClass().getMessage("prefix") + EasySpawn.getMainClass().getMessage("set_spawn_success"));
			} else {
				player.sendMessage(EasySpawn.getMainClass().getMessage("prefix") + EasySpawn.getMainClass().getMessage("no_permission"));
			}
		}
		return false;
	}
}