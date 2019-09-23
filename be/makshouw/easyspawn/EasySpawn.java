package be.makshouw.easyspawn;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import be.makshouw.easyspawn.commands.SpawnCommands;
import be.makshouw.easyspawn.listeners.PlayerJoinEvent;

public class EasySpawn extends JavaPlugin {

	public static EasySpawn instance;
	public HashMap<String, String> configResult = new HashMap<String, String>();

	@Override
	public void onEnable() {
		instance = this;
		
		Bukkit.getLogger().info("----------------------------------------------------------------");
		Bukkit.getLogger().info("EasySpawn by MAKSHOUW");
		Bukkit.getLogger().info("This is a FREE PLUGIN. If you want the source code, let me know.");
		Bukkit.getLogger().info("----------------------------------------------------------------");

		getConfig().options().copyDefaults(true);
		saveConfig();
		
		rlConfig();

		getCommand("spawn").setExecutor(new SpawnCommands());
		getCommand("setspawn").setExecutor(new SpawnCommands());

		if (getMessage("teleport_on_connect").equals("true")) {
			PluginManager pluginManager = Bukkit.getServer().getPluginManager();
			pluginManager.registerEvents(new PlayerJoinEvent(), this);
		}
	}

	public void rlConfig() {
		reloadConfig();
		
		configResult.clear();
		
		configResult.put("teleport_accepted", getConfig().getString("message.teleport_accepted").replace("&", "§"));
		configResult.put("sender_is_not_player", getConfig().getString("message.sender_is_not_player").replace("&", "§"));
		configResult.put("no_permission", getConfig().getString("message.no_permission").replace("&", "§"));
		configResult.put("set_spawn_success", getConfig().getString("message.set_spawn_success").replace("&", "§"));
		configResult.put("config_reloaded_success", getConfig().getString("message.config_reloaded_success").replace("&", "§"));
		
		configResult.put("prefix", getConfig().getString("main.prefix").replace("&", "§"));
		configResult.put("teleport_on_connect", getConfig().getString("main.teleport_on_connect"));
		configResult.put("send_teleport_message_on_join", getConfig().getString("main.send_teleport_message_on_join"));
		
		configResult.put("setspawn_permission", getConfig().getString("permission.setspawn_permission").replace("&", "§"));
		configResult.put("reload_config_permission", getConfig().getString("permission.reload_config_permission").replace("&", "§"));
		
		configResult.put("spawn_loc", getConfig().getString("location.loc"));
	}
	
	public String getMessage(String s) {
		if(configResult.get(s) != null) {
			return configResult.get(s).toString();
		} else {
			return "Hello, i found a problem in the configuration file. Reset the configuration and contact me if the problem persists. Error code: "+s;
		}
	}
	
	public Location StringToLocation(String locationString) {
		Location loc = null;
		String[] parties = locationString.split(":");
		
		String worldName = parties[0];
		
		double x = Double.valueOf(parties[1]);
		double y = Double.valueOf(parties[2]);
		double z = Double.valueOf(parties[3]);
		float yaw = Float.valueOf(parties[4]);
		float pitch = Float.valueOf(parties[5]);
		
		loc = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
		
		return loc;
	}
	
	public String RealLocationToOneLine(Location location) {
		String worldName = location.getWorld().getName();
		
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		
		float yaw = location.getYaw();
		float pitch = location.getPitch();
		
		return worldName+":"+x+":"+y+":"+z+":"+yaw+":"+pitch;
	}

	@Override
	public void onDisable() {
		this.saveConfig();
	}

	public static EasySpawn getMainClass() {
		return instance;
	}

}
