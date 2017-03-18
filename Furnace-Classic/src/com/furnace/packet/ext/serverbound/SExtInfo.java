package com.furnace.packet.ext.serverbound;

import java.io.IOException;

import com.furnace.Logger;
import com.furnace.conn.Connection;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketHandler;

public class SExtInfo implements Packet {

	public String appName;
	public short extenstionCount;

	public void read(ByteBufferIn in) throws IOException {
		appName = in.readString();
		extenstionCount = in.readShort();
	}

	public void write(ByteBufferOut out) {
		
	}

	public byte getID() {
		return 0x10;
	}
	
	public static class Handler implements PacketHandler<SExtInfo> {
		public void handle(SExtInfo packet, Connection connection) {
			Logger.log(" " + packet.appName + " has " + packet.extenstionCount + " extensions.");
			connection.setExtensionSupportCount(packet.extenstionCount);
		}
	}
}
