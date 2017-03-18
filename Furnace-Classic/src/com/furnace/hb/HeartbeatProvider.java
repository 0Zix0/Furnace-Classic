package com.furnace.hb;

public interface HeartbeatProvider {

	public String sendHeartbeat(int port, int max, String name, boolean isPublic, int version, String salt, int userCount);
}
