package com.furnace.packet.ext.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CExtEntry implements Packet {

	public String extName;
	public int version;
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeString(extName);
		out.writeInt(version);
	}

	public byte getID() {
		return 0x11;
	}
}
