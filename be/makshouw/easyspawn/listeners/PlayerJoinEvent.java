package be.makshouw.easyspawn.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import be.makshouw.easyspawn.EasySpawn;

public class PlayerJoinEvent implements Listener{
	
	@EventHandler
	public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		Location spawnLocation = EasySpawn.getMainClass().StringToLocation(EasySpawn.getMainClass().getMessage("spawn_loc"));
		
		player.teleport(spawnLocation);
		
		if(EasySpawn.getMainClass().getMessage("send_teleport_message_on_join").equals("true")) {
			player.sendMessage(EasySpawn.getMainClass().getMessage("prefix")+""+EasySpawn.getMainClass().getMessage("teleport_accepted"));
		}
	}
}
