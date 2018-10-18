package me.stewiestudio.buildersRatingSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private LinkedList<RankedPlayer> players = new LinkedList<>();
	private DatabaseConnection conn = null;;
	public void onEnable() {
		Bukkit.getServer().getLogger().info("[BuildersRatingSystem] was activated");
		//TODO: register event listeners
		Bukkit.getServer().getPluginManager().registerEvents(new MoveListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ConnectListener(this),this);
		conn = DatabaseConnection.createConnection(prepareDataFolder());
	}
	public void onDisable() {
		Bukkit.getServer().getLogger().info("[BuildersRatingSystem] was disabled");
	}
	public void connect(RankedPlayer rankedPlayer) {
		players.add(rankedPlayer);
	}
	public void disconnect(RankedPlayer p) {
		players.remove(p);
	}
	public LinkedList<RankedPlayer> getPlayers() {
		return players;
	}
	public File prepareDataFolder() {
		try {
			if(!(getDataFolder().exists())) {
				getDataFolder().mkdirs();
			}
		}catch(Exception ex) {
			Bukkit.getServer().getLogger().info("Error while loading config.txt");
		}
		return getDataFolder();
	}
}