package com.furnace;

import java.sql.Time;

public class Logger {

	public static void log(String msg) {
		System.out.println("[" + new Time(System.currentTimeMillis()) + "] : " + msg);
	}
	
	public static void err(String msg) {
		System.err.println("[" + new Time(System.currentTimeMillis()) + "] : " + msg);
	}
}
