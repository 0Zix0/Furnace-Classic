package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CPositionOrientationUpdate implements Packet {

	public byte playerID;
	public short deltaX;
	public short deltaY;
	public short deltaZ;
	public byte yaw;
	public byte pitch;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeByte(playerID);
		out.writeShort(deltaX);
		out.writeShort(deltaY);
		out.writeShort(deltaZ);
		out.writeByte(yaw);
		out.writeByte(pitch);
	}

	public byte getID() {
		return 0x09;
	}
}
