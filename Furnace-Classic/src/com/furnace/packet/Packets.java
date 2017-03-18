package com.furnace.packet;

import java.util.HashMap;

import com.furnace.packet.clientbound.CDespawnPlayer;
import com.furnace.packet.clientbound.CDisconnectPlayer;
import com.furnace.packet.clientbound.CLevelDataChunk;
import com.furnace.packet.clientbound.CLevelFinalize;
import com.furnace.packet.clientbound.CLevelInitialize;
import com.furnace.packet.clientbound.CMessage;
import com.furnace.packet.clientbound.COrientationUpdate;
import com.furnace.packet.clientbound.CPing;
import com.furnace.packet.clientbound.CPositionOrientationUpdate;
import com.furnace.packet.clientbound.CPositionUpdate;
import com.furnace.packet.clientbound.CServerID;
import com.furnace.packet.clientbound.CSetBlock;
import com.furnace.packet.clientbound.CSpawnPlayer;
import com.furnace.packet.clientbound.CTeleport;
import com.furnace.packet.clientbound.CUpdateUserType;
import com.furnace.packet.ext.clientbound.CEnvSetMapAppearance;
import com.furnace.packet.ext.clientbound.CExtEntry;
import com.furnace.packet.ext.clientbound.CExtInfo;
import com.furnace.packet.ext.clientbound.CSetClickDistance;
import com.furnace.packet.ext.serverbound.SExtEntry;
import com.furnace.packet.ext.serverbound.SExtInfo;
import com.furnace.packet.serverbound.SMessage;
import com.furnace.packet.serverbound.SPlayerID;
import com.furnace.packet.serverbound.SPosition;
import com.furnace.packet.serverbound.SSetBlock;

public enum Packets {

	STANDARD(0)
	{
		{
			this.registerPacket(0x00, PacketDirection.CLIENTBOUND, CServerID.class, null);
			this.registerPacket(0x01, PacketDirection.CLIENTBOUND, CPing.class, null);
			this.registerPacket(0x02, PacketDirection.CLIENTBOUND, CLevelInitialize.class, null);
			this.registerPacket(0x03, PacketDirection.CLIENTBOUND, CLevelDataChunk.class, null);
			this.registerPacket(0x04, PacketDirection.CLIENTBOUND, CLevelFinalize.class, null);
			this.registerPacket(0x06, PacketDirection.CLIENTBOUND, CSetBlock.class, null);
			this.registerPacket(0x07, PacketDirection.CLIENTBOUND, CSpawnPlayer.class, null);
			this.registerPacket(0x08, PacketDirection.CLIENTBOUND, CTeleport.class, null);
			this.registerPacket(0x09, PacketDirection.CLIENTBOUND, CPositionOrientationUpdate.class, null);
			this.registerPacket(0x0A, PacketDirection.CLIENTBOUND, CPositionUpdate.class, null);
			this.registerPacket(0x0B, PacketDirection.CLIENTBOUND, COrientationUpdate.class, null);
			this.registerPacket(0x0C, PacketDirection.CLIENTBOUND, CDespawnPlayer.class, null);
			this.registerPacket(0x0D, PacketDirection.CLIENTBOUND, CMessage.class, null);
			this.registerPacket(0x0E, PacketDirection.CLIENTBOUND, CDisconnectPlayer.class, null);
			this.registerPacket(0x0F, PacketDirection.CLIENTBOUND, CUpdateUserType.class, null);

			this.registerPacket(0x00, PacketDirection.SERVERBOUND, SPlayerID.class, new SPlayerID.Handler());
			this.registerPacket(0x05, PacketDirection.SERVERBOUND, SSetBlock.class, new SSetBlock.Handler());
			this.registerPacket(0x08, PacketDirection.SERVERBOUND, SPosition.class, new SPosition.Handler());
			this.registerPacket(0x0D, PacketDirection.SERVERBOUND, SMessage.class, new SMessage.Handler());
		}
	},
	EXTENDED(1)
	{
		{
			// Start Standard Packets
			this.registerPacket(0x00, PacketDirection.CLIENTBOUND, CServerID.class, null);
			this.registerPacket(0x01, PacketDirection.CLIENTBOUND, CPing.class, null);
			this.registerPacket(0x02, PacketDirection.CLIENTBOUND, CLevelInitialize.class, null);
			this.registerPacket(0x03, PacketDirection.CLIENTBOUND, CLevelDataChunk.class, null);
			this.registerPacket(0x04, PacketDirection.CLIENTBOUND, CLevelFinalize.class, null);
			this.registerPacket(0x06, PacketDirection.CLIENTBOUND, CSetBlock.class, null);
			this.registerPacket(0x07, PacketDirection.CLIENTBOUND, CSpawnPlayer.class, null);
			this.registerPacket(0x08, PacketDirection.CLIENTBOUND, CTeleport.class, null);
			this.registerPacket(0x09, PacketDirection.CLIENTBOUND, CPositionOrientationUpdate.class, null);
			this.registerPacket(0x0A, PacketDirection.CLIENTBOUND, CPositionUpdate.class, null);
			this.registerPacket(0x0B, PacketDirection.CLIENTBOUND, COrientationUpdate.class, null);
			this.registerPacket(0x0C, PacketDirection.CLIENTBOUND, CDespawnPlayer.class, null);
			this.registerPacket(0x0D, PacketDirection.CLIENTBOUND, CMessage.class, null);
			this.registerPacket(0x0E, PacketDirection.CLIENTBOUND, CDisconnectPlayer.class, null);
			this.registerPacket(0x0F, PacketDirection.CLIENTBOUND, CUpdateUserType.class, null);

			this.registerPacket(0x00, PacketDirection.SERVERBOUND, SPlayerID.class, new SPlayerID.Handler());
			this.registerPacket(0x05, PacketDirection.SERVERBOUND, SSetBlock.class, new SSetBlock.Handler());
			this.registerPacket(0x08, PacketDirection.SERVERBOUND, SPosition.class, new SPosition.Handler());
			this.registerPacket(0x0D, PacketDirection.SERVERBOUND, SMessage.class, new SMessage.Handler());
			// End Standard Packets
			
			// Start Extended Packets
			this.registerPacket(0x10, PacketDirection.CLIENTBOUND, CExtInfo.class, null);
			this.registerPacket(0x11, PacketDirection.CLIENTBOUND, CExtEntry.class, null);
			this.registerPacket(0x12, PacketDirection.CLIENTBOUND, CSetClickDistance.class, null);
			this.registerPacket(0x1E, PacketDirection.CLIENTBOUND, CEnvSetMapAppearance.class, null);
			
			this.registerPacket(0x10, PacketDirection.SERVERBOUND, SExtInfo.class, new SExtInfo.Handler());
			this.registerPacket(0x11, PacketDirection.SERVERBOUND, SExtEntry.class, new SExtEntry.Handler());
			// End Extended Packets
		}
	};
	
	private final int id;
	private final HashMap<PacketDirection, HashMap<Integer, Class <? extends Packet>>> packets = new HashMap<PacketDirection, HashMap<Integer, Class <? extends Packet>>>();
	private final HashMap<Class <? extends Packet>, PacketHandler<? extends Packet>> handlers = new HashMap<Class <? extends Packet>, PacketHandler<? extends Packet>>();

	private Packets(int id) {
		this.id = id;
		packets.put(PacketDirection.CLIENTBOUND, new HashMap<Integer, Class <? extends Packet>>());
		packets.put(PacketDirection.SERVERBOUND, new HashMap<Integer, Class <? extends Packet>>());
	}
	
	protected Packets registerPacket(int id, PacketDirection direction, Class <? extends Packet> packet, PacketHandler<? extends Packet> handler) {
		packets.get(direction).put(id, packet);
		handlers.put(packet, handler);
		return this;
	}
	
	public Packet getPacket(int id, PacketDirection direction) {
		try {
			return packets.get(direction).get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
	public PacketHandler<? extends Packet> getHandler(Class<? extends Packet> packet) {
		return handlers.get(packet);
	}
	
	public int getId() {
		return id;
	}
}
