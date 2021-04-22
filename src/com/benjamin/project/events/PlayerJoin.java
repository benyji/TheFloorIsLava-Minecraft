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
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Benjamin
 *
 */
public class PlayerJoin implements Listener { // "implements Listener" is in accordance to Main.java's "Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);"
	
	// Declaring Variables:
	World world = Bukkit.createWorld(new WorldCreator("lobby")); // Load the lobby world into the newly connected players computer's RAM when called

	/**
	 * joinEvent
	 * Responsible for: Handling Player Join requests (connecting to the server)
	 * @param e
	 */
	@EventHandler
	public void joinEvent (PlayerJoinEvent e) {
		// Declaring Variables:
		Player player = e.getPlayer(); // Get the connecting player and store them in this variable.
		
		player.teleport(new Location(world, -96.538, 4, -48.431)); // Teleport them to the lobby.
	}
	// end of joinEvent method
}
// end of PlayerJoin class
