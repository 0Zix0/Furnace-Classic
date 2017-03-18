package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CSetBlock implements Packet {

	public short x;
	public short y;
	public short z;
	public byte blockType;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(z);
		out.writeByte(blockType);
	}

	public byte getID() {
		return 0x06;
	}
}
