package com.furnace.packet;

import java.io.IOException;

import com.furnace.data.ByteBufferIn;
import com.furnace.data.ByteBufferOut;

public interface Packet {

	void read(ByteBufferIn in) throws IOException;
	void write(ByteBufferOut out);
	byte getID();
}