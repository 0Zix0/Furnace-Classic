package com.furnace.conn;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import com.furnace.FurnaceClassic;
import com.furnace.Logger;
import com.furnace.c.api.Player;
import com.furnace.c.api.Position;
import com.furnace.c.api.Server;
import com.furnace.c.api.World;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.data.FurnaceWorld;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketDirection;
import com.furnace.packet.PacketHandler;
import com.furnace.packet.Packets;
import com.furnace.packet.clientbound.CLevelDataChunk;
import com.furnace.packet.clientbound.CLevelFinalize;
import com.furnace.packet.clientbound.CLevelInitialize;
import com.furnace.packet.clientbound.CServerID;
import com.furnace.packet.clientbound.CTeleport;
import com.furnace.packet.ext.clientbound.CEnvSetMapAppearance;

public class Connection implements Runnable, Player {

	private Socket socket;
	private ByteBufferIn input;
	private DataOutputStream outputStream;
	
	private boolean connected = false;
	private boolean useCPE = false;
	private boolean waitForExtensions = false;
	private int receivedExtensionInfoCount = 0;
	private int clientExtensionSupportCount = 0;
	
	private ArrayList<String> extensions = new ArrayList<String>();
	
	private byte entityId;
	private Packets packets;
	private FurnaceWorld world;
	private Position position = new Position(0, 0, 0, (byte) 0, (byte) 0);
	
	private Server server;
	
	// PlayerData
	private String name = "N/A";
	// EndPlayerData
	
	public Connection(Socket socket, FurnaceClassic server) {
		this.socket = socket;
		this.server = server;
		this.packets = Packets.EXTENDED;
	}
	
	public void run() {
		try {
			input = new ByteBufferIn(new DataInputStream(socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());
			connected = true;
			
			while(connected) {
				byte id = input.readByte();
				//Logger.log("Received packet with ID '0x" + Integer.toHexString(id) + "'.");

				Packet p = packets.getPacket(id, PacketDirection.SERVERBOUND);
				if(p != null) {
					p.read(input);
				}
				PacketHandler handler = packets.getHandler(p.getClass());
				if(handler != null) {
					handler.handle(p, this);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendPacket(Packet packet) {
		try {
			ByteBufferOut bbo = new ByteBufferOut();
			packet.write(bbo);
			byte[] payload = bbo.getCompletePacket(packet.getID());
			outputStream.write(payload);
			FileOutputStream fos = new FileOutputStream(new File("packet.dat"));
			fos.write(payload);
			fos.close();
			Logger.log("Sending packet with ID '" + Integer.toHexString(packet.getID()) + "', size: '" + payload.length + "'.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setWorld(FurnaceWorld world) {
		try {
			sendWorld(world);
		} catch(Exception e) {
			
		}
	}
	
	public void sendWorld(FurnaceWorld world) throws IOException {
		if(this.world != null) {
			world.reclaimEid(entityId);
		}
		byte[] worldData = world.getWorldData();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(bos);
		DataOutputStream dos = new DataOutputStream(gos);
		
		dos.writeInt(worldData.length);
		dos.write(worldData);
		dos.close();
		gos.close();
		byte[] gzip = bos.toByteArray();
		bos.close();
		
		sendPacket(new CLevelInitialize());
		
		int position = 0;
		int length;
		int percent;
		byte[] buffer = new byte[1024];
		while (position != gzip.length) {
			length = Math.min(gzip.length - position, 1024);
			System.arraycopy(gzip, position, buffer, 0, length);
			percent = (int) (((double) (position + length) / (double) gzip.length) * 100);
			CLevelDataChunk dataChunk = new CLevelDataChunk();
			dataChunk.chunkLength = (short) length;
			dataChunk.chunkData = buffer;
			dataChunk.percentComplete = (byte) percent;
			//packetOutputStream.writePacket(new Packet3Chunk((short) length, buffer, (byte) percent));
			sendPacket(dataChunk);
			position += length;
		}
		
		CEnvSetMapAppearance appearance = new CEnvSetMapAppearance();
		appearance.texturePackUrl = "http://i.imgur.com/XWldbPA.png";
		appearance.sideBlock = 7;
		appearance.edgeBlock = 8;
		appearance.sideLevel = 31;
		appearance.cloudLevel = 31;
		appearance.maximumViewDistance = 16 * 16;
		//sendPacket(appearance);
		
		CLevelFinalize levelFinalize = new CLevelFinalize();
		levelFinalize.xSize = world.getLength();
		levelFinalize.ySize = world.getHeight();
		levelFinalize.zSize = world.getDepth();
		sendPacket(levelFinalize);
		
		Position spawn = world.getSpawn();
		System.out.println("SPAWN: " + spawn.getX() + ", " + spawn.getY() + ", " + spawn.getZ());
		
		CTeleport spawnPos = new CTeleport();
		spawnPos.playerID = 0;
		spawnPos.x = spawn.getX();
		spawnPos.y = spawn.getY();
		spawnPos.z = spawn.getZ();
		spawnPos.yaw = spawn.getYaw();
		spawnPos.pitch = spawn.getPitch();
		sendPacket(spawnPos);
		
		this.world = world;
		this.position = spawn;
	}
	
	public void doHandshake(String serverName, String motd, byte userType) {
		CServerID serverID = new CServerID();
		serverID.protocolVersion = 7;
		serverID.serverName = serverName;
		serverID.motd = motd;
		serverID.userType = userType;
		sendPacket(serverID);
		
		try {
			//TODO: probably dont cast this.
			sendWorld((FurnaceWorld) getServer().getSpawnWorld());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		connected = false;
	}
	
	public Server getServer() {
		return server;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	public void waitForExtensions() {
		waitForExtensions = true;
	}
	
	public void setExtensionSupportCount(int clientExtensionSupportCount) {
		this.clientExtensionSupportCount = clientExtensionSupportCount;
	}
	
	public void increaseReceivedExtensionInfoCount() {
		receivedExtensionInfoCount++;
	}
	
	public int getReceivedExtensionInfoCount() {
		return receivedExtensionInfoCount;
	}
	
	public int getExtensionSupportCount() {
		return clientExtensionSupportCount;
	}
	
	public void addExtenstion(String name) {
		String fin = name.replace(" ", "");
		System.out.println(fin);
		extensions.add(fin);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(short x, short y, short z) {
		position.setPosX(x);
		position.setPosY(y);
		position.setPosZ(z);
	}
}
