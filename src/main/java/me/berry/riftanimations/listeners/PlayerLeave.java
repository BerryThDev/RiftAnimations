package me.berry.riftanimations.listeners;

import me.berry.riftanimations.RiftAnimations;
import me.berry.riftanimations.classes.FloatingBlock;
import me.berry.riftanimations.utils.StandUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ListIterator;
import java.util.UUID;

public class PlayerLeave implements Listener {
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		ListIterator<FloatingBlock> it = RiftAnimations.stands.listIterator();

		while(it.hasNext()) {
			UUID eventPUUID = event.getPlayer().getUniqueId();

			if(eventPUUID.equals(it.next().getPlayerUUID())) {
				StandUtil.removeStand(it.next().getEntId(), event.getPlayer());
				it.remove();
			}
		}


	}
}
