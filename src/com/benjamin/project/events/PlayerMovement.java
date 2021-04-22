/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: PlayerJoin.java
 *****************************************/
package com.benjamin.project.events;

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author Benjamin
 *
 */
public class PlayerMovement implements Listener { // "implements Listener" is in accordance to Main.java's "Bukkit.getServer().getPluginManager().registerEvents(new PlayerMovement(), this);"
	// Declaring Variable
	Plugin plugin = Bukkit.getPluginManager().getPlugin("The_Floor_Is_Lava"); // Tells Minecraft which "plugin" is our program
	
	/**
	 * joinEvent
	 * Responsible for: Handling Player Join requests (connecting to the server)
	 * @param e
	 */
	@EventHandler
	public void playerMove(PlayerMoveEvent e) {
		// Processing
		if (plugin.getConfig().getBoolean("isPreGameActive") == true) { // If the Game Handler has written to the cache declaring the game is being initialized then:
		e.setCancelled(true); // Prevent the player from moving; this is not a glitch! It is a feature to prevent the player from effecting the games initialization! In the video, this is why the camera shakes at the start of the round.
		}
	}
	// end of playerMove method
}
// end of PlayerMovement class
