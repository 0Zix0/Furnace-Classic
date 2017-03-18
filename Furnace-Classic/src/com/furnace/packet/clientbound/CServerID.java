package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CServerID implements Packet {

	public byte protocolVersion;
	public String serverName;
	public String motd;
	public byte userType; // 0x64: OP, 0x00: Not OP
	
	public void read(ByteBufferIn in) throws IOException {
		protocolVersion = in.readByte();
		serverName = in.readString();
		motd = in.readString();
		userType = in.readByte();
	}

	public void write(ByteBufferOut out) {
		out.writeByte(protocolVersion);
		out.writeString(serverName);
		out.writeString(motd);
		out.writeByte(userType);
	}

	public byte getID() {
		return 0x00;
	}
}
