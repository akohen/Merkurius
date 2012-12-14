package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;

public class Velocity extends Component {
	private Vector2f 	speed;
	private Vector2f 	maxVector;
	private float		maxSpeed = -1f;
	private float		maxRotation = -1f;
	private float		rotation;

	public Velocity() {
		speed 		= new Vector2f(0,0);
		maxVector 	= null;
	}
	
	public Velocity(float maxX, float maxY) {
		speed 		= new Vector2f(0,0);
		maxVector 	= new Vector2f(maxX,maxY);
	}
	
	public Velocity(float maxSpeed) {
		speed 		= new Vector2f(0,0);
		maxVector 	= null;
		this.maxSpeed = maxSpeed;
	}

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
	
	/**
	 * Adds s to the length of the vector
	 * @param s
	 */
	public void addSpeed(float s) {
		float len = this.speed.length() + s;
		this.speed.normalise().scale(len);
		checkSpeed();
	}
	
	public Vector2f getMaxVector() { return maxVector; }
	public float getMaxSpeed() { return maxSpeed; }

	public float getX() { return speed.x; }
	public float getY() { return speed.y; }
	
	public void setRotation(float rotation) { this.rotation = rotation; checkSpeed(); }
	public void addRotation(float rotation) { this.rotation += rotation; checkSpeed(); }
	public float getRotation() { return this.rotation; }
	public float getMaxRotation() { return this.maxRotation; }
	public void setMaxRotation(float maxRotation) { this.maxRotation = maxRotation; }

	private void checkSpeed() {
		if( maxVector != null ) {
			speed.x = Math.min(speed.x, maxVector.x);
			speed.x = Math.max(speed.x, -maxVector.x);
			speed.y = Math.min(speed.y, maxVector.y);
			speed.y = Math.max(speed.y, -maxVector.y);
		}
		if( maxSpeed >= 0 && speed.length() > maxSpeed ) {
			speed.normalise().scale( maxSpeed );
		}
		if( maxRotation >= 0 && Math.abs(rotation) > maxRotation ) {
			rotation = Math.signum(rotation) * maxRotation;
		}
	}

}
