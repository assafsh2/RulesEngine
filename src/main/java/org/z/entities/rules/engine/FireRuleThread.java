package org.z.entities.rules.engine;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.kie.api.runtime.StatelessKieSession;
import org.z.entities.rules.engine.types.Wind;
import org.z.entities.rules.engine.utils.Utils;

public class FireRuleThread implements Runnable {

	private StatelessKieSession kSession;
	private String name;
	private StringBuffer output;
	private AtomicLong sum;
	private AtomicInteger count;

	public FireRuleThread(StatelessKieSession kSession, String name,
			StringBuffer output, AtomicLong sum, AtomicInteger count) {
		this.kSession = kSession;
		this.name = name;
		this.output = output;
		this.sum = sum;
		this.count = count;
	}

	@Override
	public void run() {

		StringBuffer sb = new StringBuffer();
		sb.append("Thread " + name + "   => ");	
		for (int i = 0; i < 20; i++) {

			Wind wind = Wind.NORTH;
			long startTime = System.currentTimeMillis();
			Boat boat1 = new Boat(33, 44, name + "_boat1");
			Utils.setTack(boat1, wind);
			Boat boat2 = new Boat(66, 77, name + "_boat2");
			Utils.setTack(boat2, wind);

			int generateOverlapped = Utils.generateOverlapped();
			int winward = Utils.getWindwardForBoats(boat1, boat2, wind);
			Fact fact = new Fact(boat1, boat2, wind, generateOverlapped,
					winward);

			kSession.execute(fact);

			Boat boat = new Boat(33, 44, name + "_single_boat" + i);
			boat.setSpeed((i + 1) * 63);
			boat.setCourse(i * 22);
			boat.setElevation(i * 44);
			kSession.execute(boat);

			long endTime = System.currentTimeMillis() - startTime;
			sb.append(endTime).append(" ");
			sum.addAndGet(endTime);
			count.incrementAndGet();
		}

		output.append("\n").append(sb.toString());
	}
}
