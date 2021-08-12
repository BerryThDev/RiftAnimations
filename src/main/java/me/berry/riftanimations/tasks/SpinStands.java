package me.berry.riftanimations.tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.berry.riftanimations.RiftAnimations;
import me.berry.riftanimations.classes.FloatingBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.InvocationTargetException;

public class SpinStands extends BukkitRunnable {
	@Override
	public void run() {
		for (FloatingBlock floatingBlock : RiftAnimations.stands) {
			Player player = Bukkit.getPlayer(floatingBlock.getPlayerUUID());
			ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
			PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
			float rotation = floatingBlock.getRotation();

			packet.getModifier().writeDefaults();
			packet.getIntegers().write(0, floatingBlock.getEntId());

			WrappedDataWatcher dataWatcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));

			EulerAngle eulerAngle = new EulerAngle(Math.toRadians(0), Math.toRadians(rotation), Math.toRadians(0));
			Vector3F vector3f = new Vector3F((float)Math.toDegrees(eulerAngle.getX()), (float)Math.toDegrees(eulerAngle.getY()), (float)Math.toDegrees(eulerAngle.getZ()));

			WrappedDataWatcher.WrappedDataWatcherObject standHeadIndex = new WrappedDataWatcher.WrappedDataWatcherObject(12, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
			dataWatcher.setObject(standHeadIndex, vector3f);

			packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

			try {
				protocolManager.sendServerPacket(player, packet);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Cannot send packet.", e);
			}

			floatingBlock.setRotation(rotation + 4F);
		}
	}
}
