package org.z.entities.rules.engine.rules;

import org.z.entities.rules.engine.utils.Utils;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.z.entities.rules.engine.Boat;
import org.z.entities.rules.engine.types.Tack;
import org.z.entities.rules.engine.types.Wind;

@Rule(name = "OppositeTack", description = "Who can move first in case of opposite tack")
public class OppositeTack {

	@Condition
	public boolean ifOppositeTack(@Fact("boat1") Boat boat1,
			@Fact("boat2") Boat boat2, @Fact("wind") Wind wind) {
		Utils.setTack(boat1, wind);
		Utils.setTack(boat2, wind);
		return boat1.getTack() != boat2.getTack();
	}

	@Action
	public void setKeepMove(@Fact("boat1") Boat boat1, @Fact("boat2") Boat boat2) {
		if (boat1.getTack() == Tack.STARBOARD) {
			boat1.setKeepMove(true);
			boat2.setKeepMove(false);
		} else {
			boat1.setKeepMove(false);
			boat2.setKeepMove(true);
		}
	}

	@Priority
	public int getPriority() {
		return 1;
	} 
}
