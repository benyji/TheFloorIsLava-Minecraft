/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: LavaBuilder.java
 *****************************************/
package com.benjamin.project.utils;

import java.util.Iterator; // Import Java's Iterator Library

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Benjamin
 *
 */
public class LavaBuilder {
	// Declaring Variable:
	Plugin plugin = Bukkit.getPluginManager().getPlugin("The_Floor_Is_Lava"); // Tells Minecraft which "plugin" is our program

	/**
	 * buildLava
	 * Responsible for: Handling Player Respawn requests 
	 * @param position1
	 * @param position2
	 */
	public void buildLava(Location position1, Location position2) {
		// Declaring Local Variables:
		/*
		 * The cuboid library gets the two corner block values defined in Game Handler and uses math to determine the location of all the blocks in between them.
		 */
		Cuboid selection = new Cuboid(position1, position2); // On request; Create a new instance of the Cuboid library and call its method 'Cuboid' with the information originally passed to the method.
		Iterator<Block> blocksIterator = selection.getBlocks().iterator(); // Store an iterator variable for the cuboid selection
		while (blocksIterator.hasNext()) { // Iterate through all the blocks in the selection.
			Block currentBlock = blocksIterator.next(); // Define the current block in the iteration.
			// Try, catch format:
			new BukkitRunnable() {
				public void run() {
					currentBlock.setType(Material.LAVA); // Replaces all of the blocks in the selection with lava.
				}
			}.runTask(plugin);
		}
	}
	// end method buildLava
}
// end class LavaBuilder
