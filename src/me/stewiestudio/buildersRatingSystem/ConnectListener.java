package me.stewiestudio.buildersRatingSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener{
	private Main main;
	public ConnectListener(Main main) {
		this.main = main;
	}
	public void onPlayerJoin(PlayerJoinEvent e) {
		main.connect(new RankedPlayer(e.getPlayer()));
	}
	public void onPlayerQuit(PlayerQuitEvent e) {
		disconnect(e.getPlayer());
	}
	public void onPlayerKick(PlayerKickEvent e) {
		disconnect(e.getPlayer());
	}
	private void disconnect(Player p) {
		for(RankedPlayer rp : main.getPlayers()) {
			if(rp.getName().equals(p.getName())) {
				rp.disconnect();
				main.disconnect(rp);
				return;
			}
		}
	}
}
