package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Velocity extends Component {
	public Vector2 	speed;
	public Vector2 	maxVector;
	public float		maxSpeed = -1f;
	public float		maxRotation = -1f;
	public float		rotation;

	public Velocity() {
		speed 		= new Vector2(0,0);
		maxVector 	= null;
	}
	
	public Velocity(float maxX, float maxY) {
		speed 		= new Vector2(0,0);
		maxVector 	= new Vector2(maxX,maxY);
	}
	
	public Velocity(float maxSpeed) {
		speed 		= new Vector2(0,0);
		maxVector 	= null;
		this.maxSpeed = maxSpeed;
	}

	public float getVelocity() {
		return speed.len();
	}

	public void setVelocity(float velocity) {
		speed = speed.nor().mul(velocity);
	}

	public void setAngle(float angle) {
		speed.setAngle(angle);
	}

	public float getAngle() {
		return speed.angle();
	}

	public void addAngle(float angle) {
		speed.setAngle(speed.angle() + angle);
	}

	public Vector2 getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2 speed) {
		this.speed = speed;
		checkSpeed();
	}
	
	public void setSpeed(float x, float y) {
		this.speed.x = x;
		this.speed.y = y;
		checkSpeed();
	}
	
	public void addSpeed(Vector2 speed) {
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
		float len = this.speed.len() + s;
		this.speed.nor().mul(len);
		checkSpeed();
	}
	
	public Vector2 getMaxVector() { return maxVector; }
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
		if( maxSpeed >= 0 && speed.len() > maxSpeed ) {
			speed.nor().mul( maxSpeed );
		}
		if( maxRotation >= 0 && Math.abs(rotation) > maxRotation ) {
			rotation = Math.signum(rotation) * maxRotation;
		}
	}

}
