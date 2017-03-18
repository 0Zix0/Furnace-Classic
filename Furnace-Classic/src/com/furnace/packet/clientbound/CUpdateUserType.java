package com.furnace.packet.clientbound;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;
import com.furnace.packet.Packet;

public class CUpdateUserType implements Packet {

	public byte userType; //0x64 for op, 0x00 for not op
	
	public void read(ByteBufferIn in) throws IOException {
		
	}

	public void write(ByteBufferOut out) {
		out.writeByte(userType);
	}

	public byte getID() {
		return 0x0F;
	}
}
