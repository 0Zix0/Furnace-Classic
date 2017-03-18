package com.furnace.hb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClassiCubeHeartbeat implements HeartbeatProvider {

	public static final String ENDPOINT = "http://www.classicube.net/server/heartbeat";
	private static final String USER_AGENT = "Mozilla/5.0";
	
	public String sendHeartbeat(int port, int max, String name, boolean isPublic, int version, String salt, int userCount) {
		try {
			String pub = "";
			if(isPublic) {
				pub = "True";
			} else {
				pub = "False";
			}
			String req = "port=" + port + "&max=" + max + "&name=" + name + "&public=" + pub + "&version=" + version + "&salt=" + salt + "&users=" + userCount;
			req = req.replace(" ", "%20");
			req += "\r\n";
			
			URL obj = new URL(ENDPOINT + "?" + req);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
	
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			return response.toString();
		} catch(Exception e) {
			return "";
		}
	}
}
