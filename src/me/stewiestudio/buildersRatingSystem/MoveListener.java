package me.stewiestudio.buildersRatingSystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
	Main main;
	public MoveListener(Main m) {
		this.main = m;
	}
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
	}
}
