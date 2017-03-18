package com.furnace.c.api;

public interface Server {

	/**
	 * Gets the name of the server.
	 * @return The servers name.
	 */
	public String getServerName();
	
	/**
	 * Gets the servers MOTD.
	 * @return The servers MOTD.
	 */
	public String getServerMOTD();
	
	/**
	 * Gets the current players connected to the server, across all worlds.
	 * @return An array of players.
	 */
	public Player[] getPlayers();
	
	/**
	 * Gets all currently loaded worlds.
	 * @return An array of worlds.
	 */
	public World[] getWorlds();
	
	/**
	 * Gets the world which the player first spawns in.
	 * @return The spawn world.
	 */
	public World getSpawnWorld();
}
