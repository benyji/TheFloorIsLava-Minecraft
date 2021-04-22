/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: PlayerRespawn.java
 *****************************************/
package com.benjamin.project.events;

import java.io.File; // Importing Java's File Library
import java.io.IOException; // Importing Java's Error Trap "IOException" Library

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.benjamin.project.utils.GenerateNewWorld; // Importing developer forums created GenerateNewWorld class, proper citation in file

/**
 * @author Benjamin
 *
 */
public class PlayerRespawn implements Listener { // "implements Listener" is in accordance to Main.java's "Bukkit.getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);"
	
	// Declaring Variables Section: 
	
	Plugin plugin = Bukkit.getPluginManager().getPlugin("The_Floor_Is_Lava"); // Tells Minecraft which "plugin" is our program

	World world = Bukkit.createWorld(new WorldCreator("lobby")); // Load the lobby world into the computers RAM
	World map_world = Bukkit.getWorld("minigame" + plugin.getConfig().getInt("gameInstances")); // Define an access variable to the temporary world folder

	GenerateNewWorld gen = new GenerateNewWorld(); // Defining an instance of the GenerateNewWorld class

	/**
	 * respawn
	 * Responsible for: Handling Player Respawn requests 
	 * @param e
	 */
	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		// Declaring Local Varibles:
		Player player = e.getPlayer(); //Store player in player variable.
		
		// A 'BukkitRunnable' is much like a Java try & catch except utilizing the minecraft server. It will attempt the following request on a individual thread.
		new BukkitRunnable() {
			public void run() {
				player.teleport(new Location(world, -96.538, 4, -48.431)); // Teleport the user back to the spawn from the RAM.
			}
		}.runTask(plugin);
		
		
		new BukkitRunnable() {
			public void run() {
				if (plugin.getConfig().getList("players").size() == 0) { // If this player is the last player from the game who respawned, then:
					Bukkit.unloadWorld(map_world, true); // Unload this world from all players in the session's computer RAM
					int lastInstance = plugin.getConfig().getInt("gameInstances") - 1; // Define the local variable of the current instance as the cache variable -1
					
					File worldFolder = new File(Bukkit.getServer().getWorldContainer(), "minigame" + lastInstance); // Get the temporary world in the main server directory with its special identifier
					// Processing:
					// Attempt to delete the world folder and contents from the server
					try {
						FileUtils.forceDelete(worldFolder);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTask(plugin);
		// Delete the players scoreboard (side menu) on respawn one second after the above request finishes. (So that the server is not overloaded)
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			public void run() {
				player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
		}, 20L);
	}
	// end of respawn method
}
// end of PlayerRespawn class
