package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.artemis.utils.Utils;
import com.badlogic.gdx.math.Vector2;

/**
 * Position component
 * @author Alexandre
 *
 */
public class Transform extends Component {
	public float x;
	public float y;
	public float rotation;
	public float scale;
	public int mapId;

	public Transform() {
		this.x = 0;
		this.y = 0;
		this.mapId = -1;
		this.rotation = 0;
		this.scale = 1;
	}

	public Transform(float x, float y) {
		this();
		this.setLocation(x, y);
	}

	public Transform(int mapId, float x, float y) {
		this(x, y);
		this.setMapId(mapId);
	}

	public Transform(int mapId, float x, float y, float rotation) {
		this(mapId, x, y);
		this.setRotation(rotation);
	}

	public Transform(int mapId, float x, float y, float rotation, float scale) {
		this(mapId, x, y, rotation);
		this.setScale(scale);
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
	
	public void setLocation(Vector2 loc) {
		this.x = loc.x;
		this.y = loc.y;
	}
	
	public void addVector(Vector2 loc) {
		this.x += loc.x;
		this.y += loc.y;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = (rotation + 360) % 360;
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
	
	public Vector2 getLocation() {
		return new Vector2(this.x, this.y);
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	
	/**
	 * Returns the difference between two Transform object
	 * @param t
	 * @return
	 */
	public Transform getDifference(Transform t) {
		//TODO: Complete fonction
		if( this.getMapId() == t.getMapId() ) {
			return new Transform(
					this.mapId, 
					this.x - t.getX(), 
					this.y - t.getY(), 
					this.rotation - t.getRotation()
				);
		}
		return null;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
