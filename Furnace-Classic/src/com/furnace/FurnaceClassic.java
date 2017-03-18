package com.furnace;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.furnace.c.api.Player;
import com.furnace.c.api.Server;
import com.furnace.c.api.World;
import com.furnace.conn.Connection;
import com.furnace.data.FurnaceWorld;
import com.furnace.hb.ClassiCubeHeartbeat;
import com.furnace.hb.HeartbeatProvider;
import com.furnace.util.Hashids;

public class FurnaceClassic implements Server, Runnable {

	public static final int PORT = 25565;
	
	private boolean isRunning = false;
	
	private ServerSocket socket;
	private Thread listenThread;
	
	private ExecutorService threadPool;
	
	private HeartbeatProvider heartbeat;
	private String salt;
	private String serverURL;

	private FurnaceWorld world;
	private FurnaceWorld world2;
	private String serverName;
	private String motd;
	
	private WorldManager worldManager;
	
	public FurnaceClassic() {
		init();
		Logger.log("Initialized classic server.");
		startListening();
	}
	
	private void init() {
		serverName = "Test Server";
		motd = "Welcome to the test server!";
		
		worldManager = new WorldManager();
		worldManager.loadAll();
		
		isRunning = true;
		threadPool = Executors.newCachedThreadPool();
		generateSalt();
		ConsoleReaderThread reader = new ConsoleReaderThread(this);
		reader.start();
	}
	
	private void generateSalt() {
		Hashids hashids = new Hashids();
		long[] numbers = new long[4];
		Random random = new Random();
		for(int i = 0; i < numbers.length; i++) {
			byte[] b = new byte[1];
			random.nextBytes(b);
			numbers[i] = random.nextInt(Short.MAX_VALUE);
		}
		salt = hashids.encode(numbers);
	}
	
	private void getServerURL() {
		heartbeat = new ClassiCubeHeartbeat();
		String response = heartbeat.sendHeartbeat(PORT, 32, "Test Server", false, 7, salt, 2);
		try {
			URL url = new URL(response);
			serverURL = response;
			Logger.log("Heartbeat succeeded!");
			Logger.log(" URL: " + response);
		} catch(MalformedURLException e) {
			Logger.log("Heartbeat failed!");
			Logger.log(response);
			serverURL = null;
		}
	}
	
	private void startListening() {
		listenThread = new Thread(this);
		listenThread.start();
	}
	
	public void run() {
		Logger.log("Server listenening on port " + PORT);
		try {
			socket = new ServerSocket(PORT);

			getServerURL();
			
			while (true) {
				Socket incoming = socket.accept();
				threadPool.submit(new Connection(incoming, this));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void stop() {
		throw new RuntimeException("FurnaceServer.stop() not implemented.");
	}

	//TODO: remove
	public FurnaceWorld getWorld() {
		return world;
	}

	//TODO: remove
	public FurnaceWorld getWorld2() {
		return world2;
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public Player[] getPlayers() {
		//TODO:
		return null;
	}

	public World[] getWorlds() {
		//TODO:
		return null;
	}
	
	public World getSpawnWorld() {
		return worldManager.getWorld("main");
	}

	public String getServerMOTD() {
		return motd;
	}
}
