package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CLevelDataChunk implements Packet {

	public short chunkLength;
	public byte[] chunkData;
	public byte percentComplete;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeShort(chunkLength);
		out.writeBytes(chunkData);
		out.writeByte(percentComplete);
	}

	public byte getID() {
		return 0x03;
	}
}
