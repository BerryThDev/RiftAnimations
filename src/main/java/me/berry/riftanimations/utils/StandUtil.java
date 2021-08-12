package me.berry.riftanimations.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class StandUtil {
	public static void removeStand(int id, Player player) {
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		int[] entIdArray = new int[1];
		entIdArray[0] = id;

		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
		packet.getIntegerArrays().write(0, entIdArray);

		try {
			protocolManager.sendServerPacket(player, packet);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}
	}
}
