package org.z.entities.rules.engine.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.z.entities.rules.engine.Boat;
import org.z.entities.rules.engine.types.Wind;
import org.z.entities.rules.engine.utils.Utils;

@Rule(name = "SameTackOverlapped", description = "Who can move first in case of same tack and overlapped")
public class SameTackOverlapped {

	@Condition
	public boolean ifOverlapped(@Fact("boat1") Boat boat1,
			@Fact("boat2") Boat boat2) {
		int overlapped = Utils.getOverlappedForBoats(boat1, boat2);
		return overlapped == 0;
	}

	@Action
	public void setKeepMove(@Fact("boat1") Boat boat1,
			@Fact("boat2") Boat boat2, @Fact("wind") Wind wind) {
		int windward = Utils.getWindwardForBoats(boat1, boat2, wind);
		if (windward == 1) {
			System.out.println("boat2 is windward");
			boat1.setKeepMove(true);
			boat2.setKeepMove(false);
		} else {
			System.out.println("boat1 is windward");
			boat1.setKeepMove(false);
			boat2.setKeepMove(true);
		}
	}

	@Priority
	public int getPriority() {	
		return 3;
	}
}
