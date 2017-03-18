package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CMessage implements Packet {

	public byte playerID;
	public String message;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeByte(playerID);
		out.writeString(message);
	}

	public byte getID() {
		return 0x0D;
	}
}
