package org.z.entities.rules.engine;

import org.z.entities.rules.engine.types.Wind;

public class Fact {
	
	private Boat boat1;
	private Boat boat2;
	private Wind wind;	
	private int overlapped;

	public Fact(Boat boat1, Boat boat2, Wind wind) { 
		this.boat1 = boat1;
		this.boat2 = boat2;
		this.wind = wind;
	}
	
	public Boat getBoat1() {
		return boat1;
	}
	public void setBoat1(Boat boat1) {
		this.boat1 = boat1;
	}
	public Boat getBoat2() {
		return boat2;
	}
	public void setBoat2(Boat boat2) {
		this.boat2 = boat2;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	
	public int getOverlapped() {
		return overlapped;
	}

	public void setOverlapped(int overlapped) {
		this.overlapped = overlapped;
	}
	
}
