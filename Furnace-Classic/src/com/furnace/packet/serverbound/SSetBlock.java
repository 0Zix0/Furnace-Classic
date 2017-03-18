package com.furnace.packet.serverbound;

import java.io.IOException;

import com.furnace.conn.Connection;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketHandler;

public class SSetBlock implements Packet {

	public short x;
	public short y;
	public short z;
	public byte mode;
	public byte blockType;
	
	public void read(ByteBufferIn in) throws IOException {
		x = in.readShort();
		y = in.readShort();
		z = in.readShort();
		mode = in.readByte();
		blockType = in.readByte();
	}

	public void write(ByteBufferOut out) {
		
	}

	public byte getID() {
		return 0x05;
	}
	
	public static class Handler implements PacketHandler<SSetBlock> {
		public void handle(SSetBlock packet, Connection connection) {
			if(packet.mode == 0x0) {
				connection.getWorld().setBlockAt(packet.x, packet.y, packet.z, (byte) 0);
			} else if(packet.mode == 0x01) {
				connection.getWorld().setBlockAt(packet.x, packet.y, packet.z, packet.blockType);
			}
			connection.getWorld().save();
		}
	}
}
