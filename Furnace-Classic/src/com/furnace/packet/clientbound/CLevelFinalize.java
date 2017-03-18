package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CLevelFinalize implements Packet {

	public short xSize;
	public short ySize;
	public short zSize;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeShort(xSize);
		out.writeShort(ySize);
		out.writeShort(zSize);
	}

	public byte getID() {
		return 0x04;
	}
}
