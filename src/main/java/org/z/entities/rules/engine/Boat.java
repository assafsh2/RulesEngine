package org.z.entities.rules.engine;

import org.z.entities.rules.engine.types.Tack;

public class Boat {

	private double lat;
	private double xLong;
	private double course;
	private Tack tack;
	private boolean keepMove;
	private String name;

	public Boat(double lat, double xLong, String name) {
		this.lat = lat;
		this.xLong = xLong;
		this.tack = Tack.NONE;
		this.keepMove = true;
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getXLong() {
		return xLong;
	}

	public void setXlong(double xLong) {
		this.xLong = xLong;
	}

	public Tack getTack() {
		return tack;
	}

	public void setTack(Tack tack) {
		this.tack = tack;
	}

	public double getCourse() {
		return course;
	}

	public void setCourse(double course) {
		this.course = course;
	}

	public boolean isKeepMove() {
		return keepMove;
	}

	public void setKeepMove(boolean keepMove) {
		this.keepMove = keepMove;
	}

	@Override
	public String toString() {
		return name + " [lat=" + lat + ", xLong=" + xLong + ", course="
				+ course + ", tack=" + tack + ", keepMove=" + keepMove + "]";
	}
}
