package com.furnace.packet;

import com.furnace.conn.Connection;

public interface PacketHandler<T extends Packet> {

	public void handle(T packet, Connection connection);
}
