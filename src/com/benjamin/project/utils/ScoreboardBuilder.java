/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: ScoreboardBuilder.java
 *****************************************/
package com.benjamin.project.utils;

import org.bukkit.Bukkit; // Import Core Minecraft Library [Made by Minecraft, not me!]

/*
 * Importing sub-directories of the previously imported Minecraft Library [Made by Minecraft, not me!]
 */
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.md_5.bungee.api.ChatColor;

/**
 * @author Benjamin
 *
 */
public class ScoreboardBuilder {

	/**
	 * buildScoreboard
	 * Responsible for: Creating and applying the side menu to the player
	 * @param player
	 */
	@SuppressWarnings("deprecation") // Simply means that some code will not function in future editions of Minecraft.
	public void buildScoreboard(Player player) {
		// Declaring Variables:
		
		// Minecraft API Variables:
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("game", "sidebar"); // unique identifier
		obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "The Floor Is Lava"); // Set the title of the side menu
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		Score seperator = obj.getScore(ChatColor.GRAY + "-----------------"); // Create a seperator
		seperator.setScore(1); // At position 1 of the menu

		Team map = board.registerNewTeam("map_identifier"); // ID
		map.addEntry(ChatColor.YELLOW.toString()); // key
		map.setPrefix(""); // We do not want to use a prefix in this case
		map.setSuffix(ChatColor.YELLOW + "-"); // Placeholder value
		obj.getScore(ChatColor.YELLOW.toString()).setScore(2); // Position 2 of the menu
		
		// The rest of the code follows the same format as above.

		Score map_heading = obj.getScore(ChatColor.GREEN.toString() + ChatColor.BOLD + "Map:");
		map_heading.setScore(3);

		Team points = board.registerNewTeam("points_int");
		points.addEntry(ChatColor.RED.toString()); // key
		points.setPrefix("");
		points.setSuffix(ChatColor.YELLOW + "-");
		obj.getScore(ChatColor.RED.toString()).setScore(4);

		Score points_heading = obj.getScore(ChatColor.GREEN.toString() + ChatColor.BOLD + "Points:");
		points_heading.setScore(5);
		
		Team round = board.registerNewTeam("round_int");
		round.addEntry(ChatColor.GREEN.toString()); // key
		round.setPrefix("");
		round.setSuffix(ChatColor.YELLOW + "-");
		obj.getScore(ChatColor.GREEN.toString()).setScore(6);
	

		Score round_heading = obj.getScore(ChatColor.GREEN.toString() + ChatColor.BOLD + "Round:");
		round_heading.setScore(7);

		Team wave = board.registerNewTeam("wave_int");
		wave.addEntry(ChatColor.AQUA.toString()); // key
		wave.setPrefix("");
		wave.setSuffix(ChatColor.YELLOW + "-");
		obj.getScore(ChatColor.AQUA.toString()).setScore(8);

		Score wave_heading = obj.getScore(ChatColor.GREEN.toString() + ChatColor.BOLD + "Time Until Lava:");
		wave_heading.setScore(9);

		Score seperator2 = obj.getScore(ChatColor.GRAY + "----------------- ");
		seperator2.setScore(10);

		player.setScoreboard(board); // Apply this configuration to the player
	}
	// end of buildScoreboard method
}
// end of ScoreboardBuilder class
