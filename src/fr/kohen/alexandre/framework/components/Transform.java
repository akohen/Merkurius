package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.artemis.utils.Utils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Position component
 * @author Alexandre
 *
 */
public class Transform extends Component {
	public float x;
	public float y;
	public float z;
	public float rotation;
	public float scale;
	public int mapId;

	public Transform() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.mapId = -1;
		this.rotation = 0;
		this.scale = 1;
	}

	public Transform(int mapId, float x, float y, float z) {
		this();
		this.setLocation(x, y, z);
		this.setMapId(mapId);
	}
	
	public Transform(int mapId, float x, float y) {
		this(mapId, x, y, 0);
	}

	public Transform(int mapId, float x, float y, float z, float rotation) {
		this(mapId, x, y, z);
		this.setRotation(rotation);
	}

	public Transform(int mapId, float x, float y, float z, float rotation, float scale) {
		this(mapId, x, y, z, rotation);
		this.setScale(scale);
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setLocation(Vector2 loc) {
		this.x = loc.x;
		this.y = loc.y;
	}
	
	public void setLocation(Vector3 loc) {
		this.x = loc.x;
		this.y = loc.y;
		this.z = loc.z;
	}
	
	public void addVector(Vector2 loc) {
		this.x += loc.x;
		this.y += loc.y;
	}
	
	public void addVector(Vector3 loc) {
		this.x += loc.x;
		this.y += loc.y;
		this.z += loc.z;
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
	
	public float getDistanceTo2D(Transform t) {
		return Utils.distance(t.x, t.y, x, y);
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
					this.x - t.x, 
					this.y - t.y, 
					this.z - t.z, 
					this.rotation - t.rotation
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
