/****************************************
 * Project: the-floor-is-lava
 * Programmer: Benjamin 
 * Date: April 21, 2021
 * Program: Main.java
 *****************************************/

//** IMPORTANT NOTE: I have removed my last name from all headers as this project will be going on my personal GitHub page!
package com.benjamin.project;

import org.bukkit.Bukkit; // Import Minecraft Multiplayer Library [Made by Minecraft, not me!]
import org.bukkit.plugin.java.JavaPlugin; // Import Minecraft Plugin (Program) Library [Made by Minecraft, not me!]

import com.benjamin.project.commands.CommandHandler; // Import Command Handler Class  [Made by me]
import com.benjamin.project.events.PlayerDeath; // Import Player Death Class [Made by me]
import com.benjamin.project.events.PlayerJoin; // Import Player Join Class [Made by me]
import com.benjamin.project.events.PlayerMovement; // Import Player Movement Class [Made by me]
import com.benjamin.project.events.PlayerRespawn; // Import Player Respawn Class [Made by me]

/**
 * @author Benjamin
 *	
 */
public class Main extends JavaPlugin { // extends Minecraft's Plugin (program) library
 
	/**
	 * onEnable
	 * Initialized on Minecraft server start
	 */
	@Override // Annotation 
	public void onEnable() {
		this.saveDefaultConfig(); // Saves the config.yml in Jar build to the server directory. I use the config.yml to write cache data throughout the program/game.
		/*
		 * When Minecraft receives player input in the chat reading "minigame" or any of the abbreviations listed in 'plugin.yml' it will initialize the Command Handler Class.
		 */
		getCommand("minigame").setExecutor(new CommandHandler()); 
		
		/*
		 * Tell Minecraft to listen for various events such as Player Movement, Player Death, Player Join, and Player Respawn. It will initialize my classes accordingly.
		 */
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerMovement(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);

		System.out.println("This program works"); // Debug message to server console, alerts myself or server administrator that the program has started without error.
	}
	// end of onEnable method
	
	/**
	 * onDisable
	 * Initialized on Minecraft server stop
	 */
	@Override// Annotation 
	public void onDisable() {
		this.getConfig().set("gameInstances", 1); // Reset cache file variable
		this.getConfig().set("isInitialLoad", true); // Reset cache file variable
		this.saveConfig(); // Make changes to disk (server)
	}
	// end of onDisable method

}
// end of Main class
