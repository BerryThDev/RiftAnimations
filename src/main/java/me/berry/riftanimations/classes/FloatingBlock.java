package me.berry.riftanimations.classes;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class FloatingBlock {
	private final int entId;
	private final UUID playerUUID;
	private int moves;
	private Location location;
	private final ItemStack head;
	private float rotation;

	public FloatingBlock(int entId, UUID playerUUID, Location location, ItemStack head) {
		this.entId = entId;
		this.playerUUID = playerUUID;
		this.moves = 0;
		this.location = location;
		this.head = head;
		this.rotation = 0;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRotation() {
		return rotation;
	}

	public ItemStack getHead() {
		return head;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getEntId() {
		return entId;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public int getMoves() {
		return moves;
	}

	public void addMove() {
		this.moves = this.moves + 1;
	}
}
