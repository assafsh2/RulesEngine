package org.z.entities.rules.engine;

public class SpeedMessage {

	private String target;
	private int speed;

	public SpeedMessage(String target, int speed) {
		this.target = target;
		this.speed = speed;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + speed;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpeedMessage other = (SpeedMessage) obj;
		if (speed != other.speed)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SpeedMessage [target=" + target + ", speed=" + speed + "]";
	}   
}
