package com.furnace.packet.ext.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CExtInfo implements Packet {

	public String appName;
	public short extenstionCount;

	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeString(appName);
		out.writeShort(extenstionCount);
	}

	public byte getID() {
		return 0x10;
	}
}
