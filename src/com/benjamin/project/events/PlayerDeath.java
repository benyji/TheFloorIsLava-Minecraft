/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: PlayerDeath.java
 *****************************************/
package com.benjamin.project.events;

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.ChatColor;

import com.benjamin.project.utils.LavaBuilder; // Import Lava Builder Class [Made by me] 


/**
 * @author Benjamin
 *
 */
public class PlayerDeath implements Listener { // "implements Listener" is in accordance to Main.java's "Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);"
	// Define Variables Section

	Plugin plugin = Bukkit.getPluginManager().getPlugin("The_Floor_Is_Lava"); // Tells Minecraft which "plugin" is our program
	World world = Bukkit.createWorld(new WorldCreator("lobby")); // Remember, the lobby was removed from the computers RAM in the Game Handler Class, we need to get it back here.

	LavaBuilder lava = new LavaBuilder(); // Create a new instance of the Lava Builder Class

	/**
	 * death
	 * Responsible for: Handling player death events
	 * @param e
	 * */
	@EventHandler // Annotation
	public void death(PlayerDeathEvent e) { 
		// Define Local Variables Section
		Player player = e.getEntity().getPlayer(); // Define the player variable.

		// Processing: 
		
		if (plugin.getConfig().getList("players").contains(player)) { // If the player who died is registered as a member of the game:

			player.sendMessage(
					"You died! Your total points were: " + ChatColor.GREEN + plugin.getConfig().getInt(player.getDisplayName())); // Send the player a message alerting them they have died and their total points.
			plugin.getConfig().set(player.getDisplayName(), null); // Remove the players points entry from the cache (as it is no longer needed)
			plugin.getConfig().getList("players").remove(player); // Remove the player from the list of registered players of the game.
			plugin.saveConfig(); // Write the changes to the disk.
			
			if (plugin.getConfig().getList("players").size() == 0) { // If all the players have died essentially, restore the cache to its original state.
				plugin.getConfig().set("currentRound", 0);
				plugin.getConfig().set("pos1", 0);
				plugin.getConfig().set("pos2", 0);
				plugin.getConfig().set("isGameActive", false);
				plugin.saveConfig();

			}
		}
	}
	// end of death method
}
// end of PlayerDeath Class
