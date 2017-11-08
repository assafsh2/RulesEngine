package org.z.entities.rules.engine.utils;

import java.util.Random;

import org.z.entities.rules.engine.Boat;
import org.z.entities.rules.engine.types.Tack;
import org.z.entities.rules.engine.types.Wind;

public class Utils {

	private static Random random = new Random();
	private static int overlapped;

	/**
	 * get the Tack (PORT,STARBOARD) for boat according to boat coordinator and
	 * wind
	 * 
	 * @param boat
	 * @param wind
	 * @return Tack.PORT\Tack.STARBOARD
	 */
	public static Tack getTackForBoat(Boat boat, Wind wind) {
		return random.nextBoolean() ? Tack.PORT : Tack.STARBOARD;
	}

	/**
	 * Check do the boats overlapped
	 * 
	 * @param boat1
	 * @param boat2
	 * @return 0 - The boats are not overlapped -1 - The boat1 is clear ahead,
	 *         boat2 is clear astern 1 - The boat2 is clear ahead, boat1 is
	 *         clear astern
	 */
	public static int getOverlappedForBoats(Boat boat1, Boat boat2) {
		return overlapped;
	}

	/**
	 * Check which boat is the windward boat
	 * 
	 * @param boat1
	 * @param boat2
	 * @return -1 - The boat1 is windward boat, boat2 is leeward boat 1 - The
	 *         boat2 is windward boat, boat1 is leeward boat
	 */
	public static int getWindwardForBoats(Boat boat1, Boat boat2, Wind wind) {
		return random.nextBoolean() ? -1 : 1;
	}

	public static void setTack(Boat boat, Wind wind) {
		if (boat.getTack() == Tack.NONE) {
			boat.setTack(getTackForBoat(boat, wind));
		}
	}

	public static void generateOverlapped() {
		int mod = random.nextInt() % 3;
		if (mod == 0) {
			overlapped = 0;
		} else if (mod == 1) {
			overlapped = 1;
		} else
			overlapped = -1;
	}
}
