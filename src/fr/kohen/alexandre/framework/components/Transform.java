package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;
import com.artemis.utils.Utils;

/**
 * Position component
 * @author Alexandre
 *
 */
public class Transform extends Component {
	private float x;
	private float y;
	private float rotation;
	private int mapId;

	public Transform() {
	}

	public Transform(float x, float y) {
		this.x = x;
		this.y = y;
		this.mapId = -1;
	}

	public Transform(int mapId, float x, float y) {
		this(x, y);
		this.mapId = mapId;
	}

	public void addX(float x) {
		this.x += x;
	}

	public void addY(float y) {
		this.y += y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(Vector2f loc) {
		this.x = loc.x;
		this.y = loc.y;
	}
	
	public void addVector(Vector2f loc) {
		this.x += loc.x;
		this.y += loc.y;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void addRotation(float angle) {
		rotation = (rotation + angle) % 360;
	}

	public float getRotationAsRadians() {
		return (float) Math.toRadians(rotation);
	}
	
	public float getDistanceTo(Transform t) {
		return Utils.distance(t.getX(), t.getY(), x, y);
	}
	
	public Vector2f getLocation() {
		return new Vector2f(this.x, this.y);
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

}
