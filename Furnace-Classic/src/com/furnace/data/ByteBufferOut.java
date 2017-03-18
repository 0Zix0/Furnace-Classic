package com.furnace.data;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import com.furnace.Logger;

public class ByteBufferOut {

	public static final int STRING_LENGTH = 64;
	public static final int BYTE_ARRAY_LENGTH = 1024;
	
	private Vector<Byte> packet = new Vector<Byte>();
	
	public void writeByte(byte b) {
		packet.add(b);
	}
	
	@Deprecated
	public void writeBytes(byte[] b) {
		for(int i = 0; i < b.length; i++) {
			packet.add(b[i]);
		}
	}
	
	public void writeInt(int i) {
		writeBytes(ByteBuffer.allocate(4).putInt(i).array());
	}
	
	public void writeString(String s) {
		if(s.length() <= STRING_LENGTH) {
			int paddingAmount = STRING_LENGTH - s.length();
			for(int i = 0; i < paddingAmount; i++) {
				s += ' ';
			}
			writeBytes(s.getBytes());
		} else {
			Logger.err("String too long! (" + s.length() + ").");
		}
	}
	
	public void writeByteArray(byte[] b) {
		if(b.length <= BYTE_ARRAY_LENGTH) {
			writeBytes(b);
		} else {
			Logger.err("Byte Array too long! (" + b.length + ").");
		}
	}
	
	public void writeShort(short s) {
		writeBytes(ByteBuffer.allocate(2).putShort(s).array());
	}
	
	private byte[] finish() {
		Byte[] res1 = new Byte[packet.size()];
		byte[] res = new byte[res1.length];
		packet.toArray(res1);
		for (int i = 0; i < res1.length; i++) {
			res[i] = res1[i];
		}
		packet.clear();
		return res;
	}

	public byte[] getCompletePacket(byte id) {
		byte[] all = finish();
		writeByte(id);
		writeBytes(all);
		return finish();
	}
}
