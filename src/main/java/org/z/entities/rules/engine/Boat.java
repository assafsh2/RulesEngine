package org.z.entities.rules.engine;

import java.util.ArrayList;
import java.util.List;

import org.z.entities.rules.engine.types.Tack;

public class Boat {

	private double lat;
	private double xLong;
	private double course;
	private Tack tack;
	private boolean keepMove;
	public String name; 
	private int speed;
	private double elevation;
	private List<SpeedMessage> speedMessageList;

	public Boat(double lat, double xLong, String name) {
		this.lat = lat;
		this.xLong = xLong;
		this.tack = Tack.NONE;
		this.keepMove = true;
		this.name = name; 
		this.speedMessageList = new ArrayList<>();
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	
	public List<SpeedMessage> getSpeedMessageList() {
		return speedMessageList;
	}

	public void setSpeedMessageList(List<SpeedMessage> speedMessageList) {
		this.speedMessageList = speedMessageList;
	}

 	public void makeSound() {
		System.out.println("Boat "+name+" is making a sound!");
	}
 	

 	public void raiseFlag() {
		System.out.println("Boat "+name+" is raising a flag!");
	}
 
 	public void reportSpeed(String target) {
 		System.out.println("Boat "+name+" is reporting the current speed "+speed+" to <"+target+">");
 		speedMessageList.add(new SpeedMessage(target, speed));
 	}

	@Override
	public String toString() {
		return "Boat [name=" + name + ", tack=" + tack + ", course=" + course
				+ ", elevation=" + elevation + ", speed=" + speed
				+ ", keepMove=" + keepMove + "]";
	}
 
}

 