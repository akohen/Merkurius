package fr.kohen.alexandre.games.space.components;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;

public class ShipAcceleration extends Component {
	private Vector2f speed;
	private Vector2f maxSpeed;

	public ShipAcceleration() {
		speed 		= new Vector2f(0,0);
		maxSpeed 	= null;
	}
	
	public ShipAcceleration(float x, float y) {
		speed 		= new Vector2f(0,0);
		maxSpeed 	= new Vector2f(x,y);
	}

	/*public Velocity(float velocity, float angle) {
		this.velocity = velocity;
		this.angle = angle;
	}*/

	public float getVelocity() {
		return speed.length();
	}

	public void setVelocity(float velocity) {
		speed = speed.normalise().scale(velocity);
	}

	public void setAngle(double angle) {
		speed.setTheta(angle);
	}

	public double getAngle() {
		return speed.getTheta();
	}

	public void addAngle(double angle) {
		speed.add(angle);
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2f speed) {
		this.speed = speed;
		checkSpeed();
	}
	
	public void setSpeed(float x, float y) {
		this.speed.x = x;
		this.speed.y = y;
		checkSpeed();
	}
	
	public void addSpeed(Vector2f speed) {
		this.addSpeed(speed.x, speed.y);
	}
	
	public void addSpeed(float x, float y) {
		this.speed.x += x;
		this.speed.y += y;
		checkSpeed();
	}
	
	public Vector2f getMaxSpeed() { return maxSpeed; }

	public float getX() { return speed.x; }
	public float getY() { return speed.y; }
	
	private void checkSpeed() {
		if( maxSpeed != null ) {
			speed.x = Math.min(speed.x, maxSpeed.x);
			speed.x = Math.max(speed.x, -maxSpeed.x);
			speed.y = Math.min(speed.y, maxSpeed.y);
			speed.y = Math.max(speed.y, -maxSpeed.y);
		}		
	}

}
