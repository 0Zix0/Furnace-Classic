package com.furnace.data;

import java.awt.image.DataBuffer;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferIn {

	public static final int STRING_LENGTH = 64;
	public static final int BYTE_ARRAY_LENGTH = 1024;
	
	private DataInputStream inputStream;
	
	public ByteBufferIn(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public short readShort() throws IOException {
		return ByteBuffer.wrap(readBytes(2)).getShort();
	}
	
	public String readString() throws IOException {
		return new String(readBytes(STRING_LENGTH));
	}
	
	public byte[] readByteArray() throws IOException {
		return readBytes(BYTE_ARRAY_LENGTH);
	}
	
	public int readInt() throws IOException {
		return ByteBuffer.wrap(readBytes(4)).getInt(); 
	}
	
	public byte[] readBytes(int amt) throws IOException {
		byte[] res = new byte[amt];
		for(int i = 0; i < amt; i++) {
			res[i] = readByte();
		}
		return res;
	}
	
	public byte readByte() throws IOException {
		return inputStream.readByte();
	}
}
