package com.furnace.c.api;

public interface World {

	/**
	 * Gets the ID of the block at the position specified.
	 * @param x X coordinate of the block.
	 * @param y Y coordinate of the block.
	 * @param z Z coordinate of the block.
	 * @return Block ID.
	 */
	public byte getBlockAt(short x, short y, short z);
	
	/**
	 * Sets the ID of the block at the position specified.
	 * @param x X coordinate of the block.
	 * @param y Y coordinate of the block.
	 * @param z Z coordinate of the block.
	 * @param block Block ID.
	 */
	public void setBlockAt(short x, short y, short z, byte block);
	
	/**
	 * Saves the world to the disk.
	 */
	public void save();
}
