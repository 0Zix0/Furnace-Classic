package com.furnace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReaderThread extends Thread {

	private final FurnaceClassic server;

	public ConsoleReaderThread(FurnaceClassic server) {
		this.server = server;
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (server.isRunning()) {
			try {
				String line = reader.readLine();

				if ("stop".equalsIgnoreCase(line)) {
					// TODO Proper command handling.
					server.stop();
				} else {
					Logger.log("Unknown command '" + line + "'");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
