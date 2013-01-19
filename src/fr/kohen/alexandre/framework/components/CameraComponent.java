package fr.kohen.alexandre.framework.components;

import java.util.List;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

public class CameraComponent extends Component {
	private Vector2 size;
	private Vector2 position;
	private float zoom;
	private float rotation;
	private String name;
	private List<Entity> entities;
	
	
	public CameraComponent(int width, int height) {
		setSize(new Vector2(width,height));
		setPosition(new Vector2(0,0));
		setZoom(1);
		setRotation(0);
	}	
	
	
	public Vector2 getSize() {
		return size;
	}
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public float getZoom() {
		return zoom;
	}
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
}
