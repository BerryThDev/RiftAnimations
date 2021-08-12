package me.berry.riftanimations.utils;

import org.bukkit.Location;

public class MathUtil {
	public static double correction(double standPoint, double playerPoint) {
		final double movementNum = 0.15;
		if(standPoint == playerPoint) return standPoint;
		else {
			double diff = standPoint - playerPoint;
			double diffAbs = Math.abs(diff);

			if(diffAbs <= movementNum) return Math.round(standPoint * 100.0) / 100.0;

			if(isNeg(diff)) return standPoint + movementNum;
			else return standPoint - movementNum;
		}
	}

	public static boolean isNeg(double num) {
		return num < 0;
	}

	public static boolean isNear(Location point, Location point1) {
		double x = Math.abs(point.getX() - point1.getX());
		double y = Math.abs(point.getY() - point1.getY());
		double z = Math.abs(point.getZ() - point1.getZ());

		return x <= 0.3 && y <= 0.3 && z <= 0.3;
	}

	public static boolean isToFar(double standPoint, double playerPoint) {
		double diff = Math.abs(standPoint - playerPoint);

		return diff >= 10;
	}
}
