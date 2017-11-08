package org.z.entities.rules.engine.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.z.entities.rules.engine.Boat;
import org.z.entities.rules.engine.utils.Utils;

@Rule(name = "SameTackNotOverlapped", description = "Who can move first in case of same tack and not overlapped")
public class SameTackNotOverlapped {

	private int overlapped;

	@Condition
	public boolean ifNotOverlapped(@Fact("boat1") Boat boat1,
			@Fact("boat2") Boat boat2) {
		overlapped = Utils.getOverlappedForBoats(boat1, boat2);
		return overlapped != 0;
	}

	@Action
	public void setKeepMove(@Fact("boat1") Boat boat1, @Fact("boat2") Boat boat2) {
		if (overlapped == 1) {
			System.out.println("boat2 is clear ahead");
			boat1.setKeepMove(false);
			boat2.setKeepMove(true);
		} else {
			System.out.println("boat1 is clear ahead");
			boat1.setKeepMove(true);
			boat2.setKeepMove(false);
		}
	}

	@Priority
	public int getPriority() {
		return 2;
	}
}
