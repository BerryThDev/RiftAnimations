package me.berry.riftanimations.tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.berry.riftanimations.RiftAnimations;
import me.berry.riftanimations.classes.FloatingBlock;
import me.berry.riftanimations.utils.MathUtil;
import me.berry.riftanimations.utils.StandUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ListIterator;

public class MoveStands extends BukkitRunnable {
	@Override
	public void run() {
		ListIterator<FloatingBlock> it = RiftAnimations.stands.listIterator();

		while(it.hasNext()) {
			FloatingBlock floatingBlock = it.next();
			ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
			Player player = Bukkit.getServer().getPlayer(floatingBlock.getPlayerUUID());
			Location playerLocation = player.getLocation();
			Location blockLocation = floatingBlock.getLocation();

			double x = MathUtil.correction(blockLocation.getX(), playerLocation.getX());
			double y = MathUtil.correction(blockLocation.getY(), playerLocation.getY());
			double z = MathUtil.correction(blockLocation.getZ(), playerLocation.getZ());

			if(MathUtil.isToFar(blockLocation.getX(), playerLocation.getX())
					|| MathUtil.isToFar(blockLocation.getY(), playerLocation.getY())
					|| MathUtil.isToFar(blockLocation.getZ(), playerLocation.getZ()) ) {

				StandUtil.removeStand(floatingBlock.getEntId(), Bukkit.getPlayer(floatingBlock.getPlayerUUID()));
				it.remove();
			} else if(floatingBlock.getMoves() >= 100) {
				StandUtil.removeStand(floatingBlock.getEntId(), Bukkit.getPlayer(floatingBlock.getPlayerUUID()));
				it.remove();
			} else {
				PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);

				packet.getModifier()
						.write(0, floatingBlock.getEntId())
						.write(1, x)
						.write(2, y)
						.write(3, z);

				try {
					protocolManager.sendServerPacket(player, packet);
				} catch (InvocationTargetException e) {
					throw new RuntimeException("Cannot send packet.", e);
				}

				floatingBlock.setLocation(new Location(floatingBlock.getLocation().getWorld(), x, y, z));

				if(MathUtil.isNear(floatingBlock.getLocation(), playerLocation)) {
					StandUtil.removeStand(floatingBlock.getEntId(), player);
					it.remove();
				}

				floatingBlock.addMove();
			}
		}
	}
}
