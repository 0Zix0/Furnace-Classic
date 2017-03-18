package com.furnace.packet.ext.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CEnvSetMapAppearance implements Packet {

	public String texturePackUrl;
	public byte sideBlock;
	public byte edgeBlock;
	public short sideLevel;
	public short cloudLevel;
	public short maximumViewDistance;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeString(texturePackUrl);
		out.writeByte(sideBlock);
		out.writeByte(edgeBlock);
		out.writeShort(sideLevel);
		out.writeShort(cloudLevel);
		out.writeShort(maximumViewDistance);
	}

	public byte getID() {
		return 0x1E;
	}
}
