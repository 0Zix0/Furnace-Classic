package com.furnace.packet.ext.serverbound;

import java.io.IOException;

import com.furnace.Logger;
import com.furnace.conn.Connection;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketHandler;

public class SExtEntry implements Packet {

	public String extName;
	public int version;
	
	public void read(ByteBufferIn in) throws IOException {
		extName = in.readString();
		version = in.readInt();
	}

	public void write(ByteBufferOut out) {

	}

	public byte getID() {
		return 0x11;
	}
	
	public static class Handler implements PacketHandler<SExtEntry> {
		public void handle(SExtEntry packet, Connection connection) {
			connection.increaseReceivedExtensionInfoCount();
			connection.addExtenstion(packet.extName);
			if(connection.getReceivedExtensionInfoCount() >= connection.getExtensionSupportCount()) {
				connection.doHandshake(connection.getServer().getServerName(), connection.getServer().getServerMOTD(), (byte)0x00);
			}
		}
	}
}
