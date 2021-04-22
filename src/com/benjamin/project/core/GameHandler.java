/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: GameHandler.java
 *****************************************/
package com.benjamin.project.core;

import java.util.ArrayList; // Import Java Array List Library

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import net.md_5.bungee.api.ChatColor;

import com.benjamin.project.utils.GenerateNewWorld; // Import GenerateNewWorld Class [Code supplied by developer help forums, please see class for more information]
import com.benjamin.project.utils.LavaBuilder; // Import Lava Builder Class [Made by me]
import com.benjamin.project.utils.ScoreboardBuilder; // Import Scoreboard Builder Class [Made by me]



/**
 * @author Benjamin
 *
 */
public class GameHandler {

	Plugin plugin = Bukkit.getPluginManager().getPlugin("The_Floor_Is_Lava"); // Tells Minecraft which "plugin" is our program
	
	// Declaring Variables Section:
	
	// Declaring imported classes as new instances
	ScoreboardBuilder scoreboard = new ScoreboardBuilder(); 
	GenerateNewWorld gen = new GenerateNewWorld();
	LavaBuilder lava = new LavaBuilder();

	ArrayList<Player> PlayerList = new ArrayList<Player>(); // Declare PlayerList Array List used to store players playing in game (Multiplayer function!)

	Location pos1; // Utilizes Minecraft's 'Location' property which stores all information about that coordinate such as X,Y,Z, Block Type, etc.
	Location pos2;

	boolean playerLoopOver; // Debounce boolean used to ensure code is not ran twice

	// PUBLIC VARIABLES (because Minecraft uses a 'public void run()' to implement delays, so to act in accordance to features such as the timer which call on these variables they must be public.
	public int count; // Used for loops
	public int timeUntilLava; // Used to store the time between lava jump events

	// self explanatory (do what they are called):
	public boolean initialCountdown; 
	public boolean debounce;
	public boolean debounce2;

	public boolean currentTimer;
	public boolean isNewRound;
	
	/**
	 * startRound
	 * Responsible for: The core functionality of the game allowing for infinite play-time through use of loops
	 */
	@SuppressWarnings("deprecation") // Just tells us that some of this code won't be supported in future Minecraft versions, doesn't effect the programs performance in anyway.
	public void startRound() {
		plugin.getConfig().set("activeGameHandlerInstance", true); // Mark the cache to alert a current instance of this class is active (to prevent the code from creating multiple instances)
		if (plugin.getConfig().getBoolean("isInitialLoad") == true) { // If this is the first time the game is run then create the template world
			new WorldCreator("minigame").createWorld();
		}
		World world = Bukkit.getWorld("minigame"); // Load the template world into the computers RAM
		
		ArrayList<Location> maps = new ArrayList<Location>(); // ArrayList which stores all the possible maps.
	
		GenerateNewWorld.copyWorld(world, world.getName() + plugin.getConfig().getInt("gameInstances")); // Utilize the code provided by the help forums to copy the template world into a temporary world for this active session
		World tempWorld = Bukkit.getWorld(world.getName() + plugin.getConfig().getInt("gameInstances")); // Store this temporary world for access in code.
		
		// SPAWN LOCATIONS DEPENDANT ON MAP
		
		Location map1 = new Location(tempWorld, -73.513, 47, 338.402); // X,Y,Z Coordinates of the 'Golden Block' seen on the map where the player initially spawns.
		Location map2 = new Location(tempWorld, 169.34, 64, 825.389);
		Location map3 = new Location(tempWorld, -25.414, 54, 1034.453);
		
		// My code was missing this block in the demonstration! That is why it took so long to get the third map, haha!
		maps.add(map1);
		maps.add(map2);
		maps.add(map3);
		
		// Add this game instance to the tally of amount of gameInstances run during this servers runtime.
		plugin.getConfig().set("gameInstances", plugin.getConfig().getInt("gameInstances") + 1);
		plugin.saveConfig(); // Write to config
		world.setAutoSave(false); // Do not allow changes to the temporary world to refelct upon the template.
		
		// VARIABLE INITLIZATION:
		count = 5;
		timeUntilLava = 10;
		
		debounce = false;
		initialCountdown = true;
		playerLoopOver = false;
		currentTimer = false;
		debounce2 = false;
		isNewRound = false;
		
		// PROCESSING SECTION

		Bukkit.unloadWorld("lobby", false); // Remove the lobby from the computers RAM, it is no longer needed at this time.
		
		int rand = (int) (1 + Math.random() * (maps.size() - 1 + 1)); // Randomly select a map dependant on the amount of maps in the array.

		plugin.getConfig().set("isPreGameActive", true); // Mark the initial game setup in the cache as true. (This will be the time the user is frozen to prevent the user from falling through or altering unloaded map parts)
		plugin.saveConfig(); // Write to disk.

		for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) { // Run the following code for all players in the game.
			Player currentPlayer = Bukkit.getOnlinePlayers().iterator().next(); // The defined player is the next player in the list of players.

			PlayerList.add(currentPlayer); // Add player to list of all players in game
			plugin.getConfig().createSection(currentPlayer.getDisplayName()); // Create a cache section with this users name as an identifier to store their points.

			// Build the sidebar menu for the user.
			scoreboard.buildScoreboard(currentPlayer); // Call on my ScoreboardBuilder class to handle this function.

			// Map Configuration
			switch (rand) { // Do one of the following dependant on the random number calculated previously.
			case 1:
				// THE FOLLOWING PROCESS IS THE SAME FOR ALL CASES:
				currentPlayer.teleport(map1); // Teleport the player to the map
				currentPlayer.getScoreboard().getTeam("map_identifier").setSuffix(ChatColor.YELLOW + "Hillview Park"); // Display the map name on the sidebar menu.
				// Set the X,Y,Z coordinates of the two corners of the map
				pos1 = new Location(tempWorld, -53, 46, 359); 
				pos2 = new Location(tempWorld, -95, 46, 317);
				break;
			case 2:
				currentPlayer.teleport(map2);
				currentPlayer.getScoreboard().getTeam("map_identifier").setSuffix(ChatColor.YELLOW + "Themepark");
				pos1 = new Location(tempWorld, 185, 63, 824);
				pos2 = new Location(tempWorld, 153, 63, 856);
				break;
			case 3:
				currentPlayer.teleport(map3);
				currentPlayer.getScoreboard().getTeam("map_identifier").setSuffix(ChatColor.YELLOW + "Jurassic Park");
				pos1 = new Location(tempWorld, -57, 53, 1013);
				pos2 = new Location(tempWorld, -5, 53, 1055);
				break;
			}

			// Store the X,Y,Z Coordinate of the two corners in the cache file.
			plugin.getConfig().set("pos1", pos1); 
			plugin.getConfig().set("pos2", pos2);

			plugin.saveConfig(); // Make changes to the disk.

			// LOOP SECTION
			BukkitScheduler runtime = Bukkit.getServer().getScheduler(); // Create a runtime session for this loop. (So that we can destroy this session once it is over so it does not overlap)

			runtime.scheduleAsyncRepeatingTask(plugin, new Runnable() { // Tell Minecraft to repeat this task for 20 ticks (1 second) until told to stop.
				public void run() {
					if (plugin.getConfig().getList("players").size() > 0) { // As long as there are players in the game, run this:
						if (count != -1) { // If the count variable is not negative:
							if (count != 0) { // If the count variable is not 0:
								if (isNewRound == false) { // If this is the first round:
									System.out.println("The round will start in: " + ChatColor.YELLOW + count); // Tell the console how long until the players can move around and begin
									currentPlayer.sendMessage("The round will start in: " + ChatColor.YELLOW + count); // Alert all users one by one when the round (game) starts.
								}
								count--; // Decrease the interger value of the variable by one to mask one second changes.
							} else { // Once the count reaches 0:
								if (debounce == false) { // If the game has not run this code before, then:
									System.out.println(ChatColor.GREEN + "FIND SAFTEY!!"); // Alert the console that players can move
									currentPlayer.sendMessage(ChatColor.GREEN + "FIND SAFTEY!!"); // Tell the player that they must find saftey as the timer for lava has started
									plugin.getConfig().set("isPreGameActive", false); // Tell the cache that the real game has started and setup has concluded.
									plugin.saveConfig(); // Make changes to disk.
									debounce = true; //Enable the debounce to prevent this code from being run again in this session.
								}

								initialCountdown = false; // Mark the initial round countdown as over.
								count = 0; // Ensure the count stays at 0 and does not change.
								
								if (timeUntilLava != -1) { // If the lava timer is not negative, run:
									if (timeUntilLava != 0) { // If the lava timer is not 0, run:
										if (isNewRound == true) { // If lava has not been placed yet:
											int points = getPlayerPoints(currentPlayer, true); // Call on the getPlayerPoints method to get the players current points from the cache.
											plugin.getConfig().set(currentPlayer.getDisplayName(), points); // Add the corresponding amount to the players points for completing objectives.
											plugin.saveConfig(); // Make these changes to the cache. 
											currentPlayer.getScoreboard().getTeam("points_int")
													.setSuffix(ChatColor.RED + String.valueOf(points)); // Display the users points on the side menu, updating every second.
										}
										currentPlayer.getScoreboard().getTeam("wave_int")
												.setSuffix(ChatColor.RED + String.valueOf(timeUntilLava)); // Display the amount of time before lava is placed to the user.

										timeUntilLava--; // Remove one second from the countdown of lava placement each second.

									} else { // Once the lava countdown has reached 0, run:
										int points = getPlayerPoints(currentPlayer, false); // Add points to the player for completing this objective

										plugin.getConfig().set(currentPlayer.getDisplayName(), points); // Store the players new total points in cache file.										
										plugin.getConfig().set("round", plugin.getConfig().getInt("round") + 1); // Add 1 to the current round number representing the amount of times lava has risen.
										plugin.saveConfig(); // Write these changes to the disk.
										
										int round = plugin.getConfig().getInt("round"); // Get the round variable as written in the cache file.
										int currentRound = plugin.getConfig().getInt("round") - 1; // Get the current round by removing the newly added 1 to the amount for the local variable.

										currentTimer = false; 

										currentPlayer.getScoreboard().getTeam("wave_int")
												.setSuffix(ChatColor.YELLOW + String.valueOf(0)); // Display '0' in place of how long until lava will be placed. (As it has hit 0 it will now place)
										currentPlayer.getScoreboard().getTeam("round_int")
												.setSuffix(ChatColor.YELLOW + String.valueOf(round)); // Display the current round to the user.
										currentPlayer.getScoreboard().getTeam("points_int")
												.setSuffix(ChatColor.RED + String.valueOf(points)); // Display the users updated points
										
										// Get the Y value of the corner blocks and add the amount of rounds that have past to the initial Y. This will cause the lava to rise one block each round. (2 rounds = inital value + 2)
										pos1.setY(pos1.getY() + currentRound); 
										pos2.setY(pos2.getY() + currentRound);
										
										// Write these changes to the disk:
										plugin.getConfig().set("pos1", pos1);
										plugin.getConfig().set("pos2", pos2);
										plugin.saveConfig();
										
										// Build the lava blocks on the map
										lava.buildLava(pos1, pos2);
										
										// Get the initial amount until lava (10), multiply 5 by the amount of rounds that have past and add them together.
										timeUntilLava = 10 + (5 * round); // First calculate 5 x amount of rounds then add
										count++; // add to the loop variable.
										isNewRound = true; // Ensure the code knows that this is no longer the first round from this point forward.
									}
								}

							}
						}
					} else {
						System.out.println("Player died, ending loop"); // Tell the server console the game session has ended as all players died)
						runtime.cancelTasks(plugin); // Error-Trap; Cancel this runtime session from continuing which would overlap with the next round and cause mayhem.
					}
				}

			}, 0L, 20L); // loop every 20 ticks (1 second)
		}
		// Save the amount of players remaining
		plugin.getConfig().set("players", PlayerList);
		plugin.saveConfig();
	}
	// end of startRound method

	/**
	 * getPlayerPoints
	 * Responsible for: returning the players new points dependant on the objective they complete.
	 * @param currentPlayer
	 * @param identifier
	 * @return plugin.getConfig().getInt(currentPlayer.getDisplayName()) + # of new points
	 * */
	private int getPlayerPoints(Player currentPlayer, boolean identifier) {
		if (identifier == true) { // True = points each second alive
			return plugin.getConfig().getInt(currentPlayer.getDisplayName()) + 5;
		} else { // False = points for surviving round
			return plugin.getConfig().getInt(currentPlayer.getDisplayName()) + 10;
		}
	}
	// end of getPlayerPoints method
}
// end of GameHandler (finally haha!)