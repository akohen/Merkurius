package fr.kohen.alexandre.framework.components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;
import com.artemis.Entity;

public class Camera extends Component {
	protected int		screenWidth;	// Width of the viewport
	protected int		screenHeight;	// Height of the viewport
	protected int		screenX;
	protected int		screenY;
	protected float		screenRotation;	// Rotation of the viewport on the screen (in degrees)
	protected String	name;			// Name of the camera
	protected Entity	mouse;
	protected List<Entity> entities;
	
	
	/**
	 * Create a default camera
	 */
	public Camera() {
		this(0,0,0,0,0);
	}
	
	/**
	 * Create a camera of the defined size
	 * @param camera width
	 * @param camera height
	 */
	public Camera(int x, int y) {
		this(x,y,0,0,0);
	}
	
	/**
	 * @param screenX Position of the viewport on the screen
	 * @param screenY Position of the viewport on the screen
	 * @param width Size of the viewport on the screen
	 * @param height Size of the viewport on the screen
	 * @param screenRotation Rotation of the viewport on the screen (in degrees)
	 */
	public Camera(int screenX, int screenY, int screenWidth, int screenHeight, float screenRotation) {
		this.screenX		= screenX;
		this.screenY		= screenY;
		this.screenWidth	= screenWidth;
		this.screenHeight	= screenHeight;
		this.screenRotation = screenRotation;
		this.entities 		= new ArrayList<Entity>();
	}
	
	/**
	 * @param screenX Position of the viewport on the screen
	 * @param screenY Position of the viewport on the screen
	 * @param width Size of the viewport on the screen
	 * @param height Size of the viewport on the screen
	 * @param screenRotation Rotation of the viewport on the screen (in degrees)
	 * @param name Camera name
	 */
	public Camera(int screenX, int screenY, int screenWidth, int screenHeight, float screenRotation, String name) {
		this(screenX, screenY, screenWidth, screenHeight, screenRotation);
		this.name = name;
	}
	
	public Vector2f getOffset() { return new Vector2f(screenWidth/2, screenHeight/2); }
	
	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenX() {
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	/**
	 * @return Rotation of the viewport on the screen (in degrees)
	 */
	public float getScreenRotation() {
		return screenRotation;
	}
	
	/**
	 * @return Rotation of the viewport on the screen (in radians)
	 */
	public float getScreenRotationAsRadians() {
		return (float) Math.toRadians(screenRotation);
	}

	/**
	 * @param rotation Rotation of the viewport on the screen (in degrees)
	 */
	public void setScreenRotation(float screenRotation) {
		this.screenRotation = (screenRotation + 360) % 360;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity getMouse() {
		return mouse;
	}

	public void setMouse(Entity mouse) {
		this.mouse = mouse;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	
	public void addEntity(Entity e) {
		this.entities.add(e);
	}
	
	public void clearEntities() {
		this.entities.clear();
	}
	
}
