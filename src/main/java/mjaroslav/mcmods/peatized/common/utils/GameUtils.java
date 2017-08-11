package mjaroslav.mcmods.peatized.common.utils;

public class GameUtils {
	public static int getTicksFromSeconds(int seconds) {
		return seconds * 20;
	}

	public static int getTicksFromMinutes(int minutes) {
		return minutes * 60 * 20;
	}

	public static int getTicksFromSeconds(int seconds, int mills) {
		return seconds * 20 + Math.round((mills / 1000) * 20);
	}

	public static int getTicksFromMinutes(int minutes, int seconds) {
		return minutes * 60 * 20 + seconds * 20;
	}

	public static int getTicksFromSmelting(int count) {
		return count * 200;
	}

	public static int getTicksFromSmelting(float count) {
		return Math.round(count * 200);
	}
}
