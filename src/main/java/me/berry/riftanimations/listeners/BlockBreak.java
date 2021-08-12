package me.berry.riftanimations.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.berry.riftanimations.RiftAnimations;
import me.berry.riftanimations.classes.FloatingBlock;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		// Refer to wiki.vg to see exactly how the packets work and what they require
		// Generates the random id of the stand
		int id = (int)(Math.random() * Integer.MAX_VALUE);

		Location loc = event.getBlock().getLocation().clone().add(0.5, 0, 0.5);
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

		// Sets up the first packet that spawns the stand in the center of the broke block
		PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);

		// Sets the values of the stand
		packet.getModifier()
				.write(0, id)
				.write(1, UUID.randomUUID())
				.write(2, 30)
				.write(3, loc.getX())
				.write(4, loc.getY())
				.write(5, loc.getZ());

		// Sends the packet
		try {
			protocolManager.sendServerPacket(event.getPlayer(), packet);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}

		// Sets up the second packet
		PacketContainer packet2 = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
		packet2.getModifier().writeDefaults();
		packet2.getIntegers().write(0, id);
		WrappedDataWatcher dataWatcher = new WrappedDataWatcher(packet2.getWatchableCollectionModifier().read(0));

		// Modifies the invisible and small boolean
		WrappedDataWatcher.WrappedDataWatcherObject isInvisibleIndex = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));
		dataWatcher.setObject(isInvisibleIndex, (byte) 0x20);

		WrappedDataWatcher.WrappedDataWatcherObject isSmallIndex = new WrappedDataWatcher.WrappedDataWatcherObject(11, WrappedDataWatcher.Registry.get(Byte.class));
		dataWatcher.setObject(isSmallIndex, (byte) 0x01);

		packet2.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

		// Sends the packet
		try {
			protocolManager.sendServerPacket(event.getPlayer(), packet2);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}

		// Sets up the entity equipment packet
		PacketContainer packet3 = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
		// Sets up all the needed values
		packet3.getIntegers().write(0, id);
		packet3.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
		ItemStack head = new ItemStack(event.getBlock().getType(), 1, (short) 0, event.getBlock().getData());
		packet3.getItemModifier().write(0, head);

		// Sends the packet
		try {
			protocolManager.sendServerPacket(event.getPlayer(), packet3);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}

		RiftAnimations.stands.add(new FloatingBlock(id, event.getPlayer().getUniqueId(), loc, head));
	}
}
