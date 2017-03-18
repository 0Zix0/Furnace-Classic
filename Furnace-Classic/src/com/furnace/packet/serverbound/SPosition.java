package com.furnace.packet.serverbound;

import java.io.IOException;

import com.furnace.c.api.Position;
import com.furnace.conn.Connection;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketHandler;

public class SPosition implements Packet {
	
	public byte playerID;
	public short x;
	public short y;
	public short z;
	public byte yaw;
	public byte pitch;
	
	public void read(ByteBufferIn in) throws IOException {
		playerID = in.readByte();
		x = in.readShort();
		y = in.readShort();
		z = in.readShort();
		yaw = in.readByte();
		pitch = in.readByte();
	}

	public void write(ByteBufferOut out) {

	}

	public byte getID() {
		return 0x08;
	}
	
	public static class Handler implements PacketHandler<SPosition> {
		public void handle(SPosition packet, Connection connection) {
			Position p = new Position(packet.x, packet.y, packet.z, packet.yaw, packet.pitch);
			connection.setPosition(p);
		}
	}
}
