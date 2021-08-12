package me.berry.riftanimations;

import me.berry.riftanimations.classes.FloatingBlock;
import me.berry.riftanimations.listeners.BlockBreak;
import me.berry.riftanimations.listeners.PlayerLeave;
import me.berry.riftanimations.tasks.MoveStands;
import me.berry.riftanimations.tasks.SpinStands;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class RiftAnimations extends JavaPlugin {
	public static ArrayList<FloatingBlock> stands;
	public static RiftAnimations instance;

	@Override
	public void onEnable() {
		instance = this;

		stands = new ArrayList<>();

		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new PlayerLeave(), this);

		MoveStands moveStands = new MoveStands();
		moveStands.runTaskTimer(this, 0L, 0L);
		SpinStands spinStands = new SpinStands();
		spinStands.runTaskTimer(this, 0L, 0L);
	}
}
