package com.furnace.packet.serverbound;

import java.io.IOException;

import com.furnace.Logger;
import com.furnace.conn.Connection;
import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;
import com.furnace.packet.PacketHandler;
import com.furnace.packet.clientbound.CDisconnectPlayer;
import com.furnace.packet.clientbound.CMessage;
import com.furnace.packet.clientbound.CSpawnPlayer;
import com.furnace.packet.ext.clientbound.CSetClickDistance;

public class SMessage implements Packet{

	public String message;
	
	public void read(ByteBufferIn in) throws IOException {
		in.readByte();
		message = in.readString();
	}

	public void write(ByteBufferOut out) {
		
	}

	public byte getID() {
		return 0x0D;
	}
	
	public static class Handler implements PacketHandler<SMessage> {
		public void handle(SMessage packet, Connection connection) {
			Logger.log(packet.message);
			CMessage message = new CMessage();
			message.playerID = 1;
			message.message = new String(new byte[] {(byte) 178, (byte) 219});
			connection.sendPacket(message);
			
			CSetClickDistance clickDistance = new CSetClickDistance();
			clickDistance.distance = 160 + (32 * 5);
			connection.sendPacket(clickDistance);
		}
	}
}
