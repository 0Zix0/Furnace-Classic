package com.furnace;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import com.furnace.c.api.World;
import com.furnace.data.FurnaceWorld;

public class WorldManager {
	
	private HashMap<String, World> worlds = new HashMap<String, World>();
	
	public void loadAll() {
		File dir = new File("worlds");
		File [] files = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".world");
		    }
		});

		boolean spawnWorld = false;
		
		if(files.length == 0) {
			Logger.log("No worlds found, generating one.");
			createWorld("main");
			spawnWorld = true;
		} else {
			for (File f : files) {
				String name = f.getName().split("\\.")[0];
				if(name.equalsIgnoreCase("main")) {
					spawnWorld = true;
				}
				Logger.log("Loading world '" + name + "'.");
				worlds.put(name, new FurnaceWorld(name));
			}
		}
		if(!spawnWorld) {
			Logger.log("No main world found, creating one.");
			createWorld("main");
		}
	}
	
	public void createWorld(String name) {
		FurnaceWorld world = new FurnaceWorld(name, (short)200, (short)200, (short)200);
		world.save();
		worlds.put(name, world);
	}
	
	public World getWorld(String name) {
		return worlds.get(name);
	}
}
