package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CTeleport implements Packet {

	public byte playerID;
	public short x;
	public short y;
	public short z;
	public byte yaw;
	public byte pitch;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeByte(playerID);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
	}

	public byte getID() {
		return 0x08;
	}
}
