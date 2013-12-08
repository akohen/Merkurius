package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

/**
 * Position component
 * @author Alexandre
 */
public class Transform extends Component implements Syncable {
	public Vector3 position = new Vector3();
	public float rotation = 0;
	public float scale = 1;
	public int mapId = -1;

	public Transform(int mapId, float x, float y) { this(mapId, x, y, 0, 0, 1); }
	public Transform(int mapId, float x, float y, float z) { this(mapId, x, y, z, 0, 1); }
	public Transform(int mapId, float x, float y, float z, float rotation) { this(mapId, x, y, z, rotation, 1); }
	public Transform(int mapId, float x, float y, float z, float rotation, float scale) {
		this.rotation = rotation;
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		this.mapId = mapId;
		this.scale = scale;
	}
	
	
	public Transform set(Transform transform) {
		this.position.set(transform.position);
		this.rotation 	= transform.rotation;
		this.scale 		= transform.scale;
		this.mapId 		= transform.mapId;
		return this;
	}
	
	public Transform setPosition(Vector2 loc) {
		this.position.x = loc.x;
		this.position.y = loc.y;
		return this;
	}
	
	public Transform setPosition(Vector3 pos) {
		this.position.x = pos.x;
		this.position.y = pos.y;
		this.position.z = pos.z;
		return this;
	}
	
	public Transform setRotation(float rotation) {
		this.rotation = (rotation + 360)%360;
		return this;
	}
	
	public Transform setScale(float scale) {
		this.scale = scale;
		return this;
	}
	
	public Transform setMapId(int mapId) {
		this.mapId = mapId;
		return this;
	}

	
	public Vector2 getPosition2() {
		return new Vector2(this.position.x, this.position.y);
	}

	public Vector3 getPosition() {
		return position;
	}
	@Override
	public void sync(EntityUpdate update) {
		this.position.x 	= update.getNextFloat();
		this.position.y 	= update.getNextFloat();
		this.rotation 		= update.getNextFloat();
	}
	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(this.position.x).append(" ").append(this.position.y).append(" ").append(this.rotation);
	}

}
