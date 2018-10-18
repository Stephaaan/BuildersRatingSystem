package me.stewiestudio.buildersRatingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RankedPlayer {
	private Player p;
	private int rank;
	private int blocksPlaced;
	private int blocksDestroyed;
	private int timePlayed;
	public RankedPlayer(Player p) {
		this.p = p;
		//TODO: check if player is in db if not: create
		ResultSet info = DatabaseConnection.getConnection().getPlayerInfo(p.getName());
		if(info == null) {
			//TODO: otestovat
			DatabaseConnection.getConnection().insertNewPlayer(p.getName());
		}else {
			//TODO: precitat z resultsetu data a spracovat
			
			//TODO: google resultset
		}
		
		
		try {
			rank = info.getInt("rank");
			blocksPlaced = info.getInt("blocksPlaced");
			blocksDestroyed = info.getInt("blocksDestroyed");
			timePlayed = info.getInt("timePlayed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bukkit.getServer().getLogger().info("[BuildersRatingSystem] Hrac "+ p.getName() + " sa pripojil.");
		
	}
	public String getName() {
		return p.getName();
	}
	public void disconnect() {
		DatabaseConnection.getConnection().updatePlayerInfo(p.getName(), rank, blocksPlaced, blocksDestroyed, timePlayed);
	}
}
