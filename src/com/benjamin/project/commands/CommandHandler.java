/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: CommandHandler.java
 *****************************************/
package com.benjamin.project.commands;

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.command.Command; 
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.benjamin.project.core.GameHandler; // Import Game Handler Class [Made by me]

/**
 * @author Benjamin
 *
 */
public class CommandHandler implements CommandExecutor { // "implements CommandExecutor" is in accordance to Main.java's "getCommand("minigame").setExecutor(new CommandHandler());"

	Plugin plugin = Bukkit.getPluginManager().getPlugin("The_Floor_Is_Lava"); // Tells Minecraft which "plugin" is our program

	GameHandler gh = new GameHandler(); // Create a new instance of the Game Handler Class

	/**
	 * onCommand
	 * Initialized when the player runs "/minigame" in chat
	 * @param sender
	 * @param cmd
	 * @param label
	 * @param args
	 * @return boolean
	 * 
	 */
	@Override // Annotation
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// PROCESSING SECTION
		if (cmd.getName().equals("minigame")) { // If the command equals "minigame" or any abbreviation listed in 'plugin.yml' run then:
			
			if ((sender instanceof Player)) { // Error-Trap; ensures CONSOLE (server administrator) is not attempting to initialize the game through the server console rather then inside of the game.
				// SINGULAR VARIABLE DECLARATION:
				Player player = (Player) sender; // Mark the sender of the command as a player through casting.
				/*
				 * FRAMEWORK:
				 *  /minigame start/reload
				 *  ARGS:         [0]
				 */
				if (!(args.length == 0)) { // Ensure the user did not only type '/minigame' without any other arguments.
					if (plugin.getConfig().getBoolean("isGameActive") != true) { // Error-Trap; Do not let a player initialize a new game while they are currently in one.
						/*
						 *  /minigame reload :
						 *  Reloads the values inside the cache file, (Example: allows an administrator to remove all the points from a cheater!)
						 */
						if (args[0].equalsIgnoreCase("reload")) { // If argument [0] as listed in the framework comment is equal to "reload" then:
							reloadConfig(); // call a method to reload the configuration file in the server directory.
							
							System.out.println("The config has successfully reloaded!"); // Tell the server console that the configuration file has been reloaded. (Logs user changes)
							player.sendMessage("The config has successfully reloaded!"); // Alert the player who ran the command that the changes were successful.
							
							/*
							 *   /minigame start
							 */
						} else if (args[0].equalsIgnoreCase("start")) {  // If argument [0] as listed in the framework comment is equal to "start" then:
							plugin.getConfig().set("round", 0); // Reset the current round in the cache file.
							plugin.getConfig().set("isGameActive", true); // Mark the game as active in the cache file.
							plugin.saveConfig(); // Write the changes to the disk.
							System.out.println("The game is starting..."); // Alert the server console that the game is starting.
							player.sendMessage("The game is starting..."); // Send the player who initialize the code a confirmation message.
							if (plugin.getConfig().getBoolean("activeGameHandlerInstance") == true) { // If an instance of the Game Handler Class is currently active then:
								plugin.getConfig().set("isInitialLoad", false); // Mark the boolean determining if this is the first game instance to false.
								plugin.getConfig().set("activeGameHandlerInstance", false); // Mark the boolean determining if an instance of the Game Handler class is active to false, (because below we will remove it).
								plugin.saveConfig(); // Make changes to disk.
								
								gh = null; // Delete session of Game Handler class (otherwise there will be two instances of the game happening at once which ruins the game)
								GameHandler gh = new GameHandler(); // Now that outstanding instances have been removed, initialize a new one.
								gh.startRound(); // Start the game!
								
							} else {
								
								plugin.getConfig().set("isInitialLoad", true); // Mark cache as the first game session (this is important because precautions must be met for the next sessions to make sure they don't overlap)
								plugin.saveConfig(); // Make changes to disk.
								System.out.println("Initial Load"); // Tell the server console that the first game session has commenced.
								gh.startRound(); // Start the game!
							}
						}
						
					} else { // Error-Trap receiver; what to do when it has been caught, 
						System.out.println("A game is already running!"); // Alert the console that a user attempted to begin a game while already in one.
						player.sendMessage("A game is already running!"); // Alert that player that they are already in a game and cannot start a new one.
					}
					
				} else { // Error-Trap receiver;  
					System.out.println("Invalid format, please use '/minigame start/reload'."); // Alert the console that a user did not enter all required fields to start a game.
					player.sendMessage("Invalid format, please use '/minigame start/reload'."); // Tell the player they used an invalid format.
				}

			} else {
				System.out.println("Only a player can run this command!"); // Alert console that they cannot start a game from the console.
			}

		}
		return false; // Default return statement for Minecraft commands (does not mean anything).
	}
	 // end of onCommand method
	
	/**
	 * reloadConfig
	 * Responsible for: Reloading the cache file.
	 * */
	public void reloadConfig() {
		plugin.reloadConfig(); 
	}
	// end of reloadConfig method

}
// end of CommandHandler class
