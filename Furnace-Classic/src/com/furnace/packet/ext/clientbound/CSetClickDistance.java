package com.furnace.packet.ext.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CSetClickDistance implements Packet {

	public short distance;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeShort(distance);
	}

	public byte getID() {
		return 0x12;
	}
}
