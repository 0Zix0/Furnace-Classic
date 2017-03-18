package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CSpawnPlayer implements Packet {

	public byte playerID;
	public String playerName;
	public short x;
	public short y;
	public short z;
	public byte yaw;
	public byte pitch;
	
	public void read(ByteBufferIn in) throws IOException {
		playerID = in.readByte();
		playerName = in.readString();
		x = in.readShort();
		y = in.readShort();
		z = in.readShort();
		yaw = in.readByte();
		pitch = in.readByte();
	}

	public void write(ByteBufferOut out) {
		out.writeByte(playerID);
		out.writeString(playerName);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
	}

	public byte getID() {
		return 0x07;
	}
}
