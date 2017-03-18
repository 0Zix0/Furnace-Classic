package com.furnace.packet.serverbound;

import java.io.IOException;import javax.jws.soap.SOAPBinding.Use;

import com.furnace.Logger;
import com.furnace.conn.Connection;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketHandler;
import com.furnace.packet.clientbound.CLevelFinalize;
import com.furnace.packet.clientbound.CServerID;
import com.furnace.packet.clientbound.CSpawnPlayer;
import com.furnace.packet.ext.clientbound.CExtEntry;
import com.furnace.packet.ext.clientbound.CExtInfo;

public class SPlayerID implements Packet {

	public byte protocolVersion;
	public String username;
	public String verificationKey;
	public byte padding;
	
	public void read(ByteBufferIn in) throws IOException {
		protocolVersion = in.readByte();
		username = in.readString();
		verificationKey = in.readString();
		padding = in.readByte();
	}

	public void write(ByteBufferOut out) {
		
	}

	public byte getID() {
		return 0x00;
	}

	public static class Handler implements PacketHandler<SPlayerID> {
		public void handle(SPlayerID packet, Connection connection) {
			boolean useCPE = packet.padding == 0x42 ? true : false;
			Logger.log("'" + packet.username + "' is connecting, using protocol '" + packet.protocolVersion + "', using CPE: " + useCPE + ".");
			connection.setName(packet.username);
			
			if(useCPE) {
				CExtInfo extInfo = new CExtInfo();
				extInfo.appName = "Furnace-Classic";
				extInfo.extenstionCount = 2;//TODO: actually make extenstions.
				connection.sendPacket(extInfo);

				CExtEntry extEntry1 = new CExtEntry();
				extEntry1.extName = "ClickDistance";
				extEntry1.version = 1;
				connection.sendPacket(extEntry1);

				CExtEntry extEntry2 = new CExtEntry();
				extEntry2.extName = "MessageTypes";
				extEntry2.version = 1;
				connection.sendPacket(extEntry2);
				
				CExtEntry extEntry3 = new CExtEntry();
				extEntry3.extName = "FullCP437";
				extEntry3.version = 1;
				connection.sendPacket(extEntry3);
				
				connection.waitForExtensions();
			} else {
				connection.doHandshake(connection.getServer().getServerName(), connection.getServer().getServerMOTD(), (byte)0x00);
			}
			
			//CServerID serverID = new CServerID();
			//serverID.protocolVersion = 7;
			//serverID.serverName = "Test Server";
			//serverID.motd = "Welcome to a test server!";
			//serverID.userType = 0x00;
			//connection.sendPacket(serverID);
			
			//try {
			//	connection.sendWorld(connection.getServer().getWorld());
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
		}
	}
}
