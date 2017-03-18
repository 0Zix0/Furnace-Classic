package com.furnace.c.api;

public interface Player {

	/**
	 * @return The players name.
	 */
	public String getName();
	
	/**
	 * Sets the players current name.
	 * @param name The players new name.
	 */
	public void setName(String name);
	
	/**
	 * @return The world that the player is currently in.
	 */
	public World getWorld();
	
	/**
	 * @return The position of the player.
	 */
	public Position getPosition();
	
	/**
	 * Sets the position of the player.
	 * @param position The players new position.
	 */
	public void setPosition(Position position);
	
	/**
	 * Sets the position of the player.
	 * @param x New X position of the player.
	 * @param y New Y position of the player.
	 * @param z New Z position of the player.
	 */
	public void setPosition(short x, short y, short z);
}
