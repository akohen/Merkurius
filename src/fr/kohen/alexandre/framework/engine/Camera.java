package fr.kohen.alexandre.framework.engine;

import org.newdawn.slick.geom.Vector2f;

public class Camera {
	protected Vector2f 	position;		// Position of the camera in the game world
	protected float 	rotation;		// Rotation of the camera relative to the game world (in degrees)
	protected int		mapId;			// Id of the map at which the camera is looking
	
	protected Vector2f 	screenSize;		// Size of the viewport on the screen
	protected Vector2f 	screenPosition;	// Position of the viewport on the screen
	protected float		screenRotation;	// Rotation of the viewport on the screen (in degrees)
	
	protected String	name;
	
	
	/**
	 * Create a default camera
	 */
	public Camera() {
		this(0,0,0,-1,0,0,0,0,0);
	}
	
	/**
	 * Create a camera of the defined size
	 * @param camera width
	 * @param camera height
	 */
	public Camera(float x, float y) {
		this(0,0,0,-1,x,y,0,0,0);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param rotation
	 * @param mapId
	 * @param screenX
	 * @param screenY
	 * @param width
	 * @param height
	 * @param screenRotation
	 */
	public Camera(float x, float y, float rotation, int mapId, float screenX, float screenY, float width, float height, float screenRotation) {
		this.position 		= new Vector2f(x,y);
		this.rotation 		= rotation;
		this.mapId 			= mapId;
		this.screenSize 	= new Vector2f(screenX,screenY);
		this.screenPosition = new Vector2f(width,height);
		this.screenRotation = screenRotation;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param rotation
	 * @param mapId
	 * @param screenX
	 * @param screenY
	 * @param width
	 * @param height
	 * @param screenRotation
	 * @param name
	 */
	public Camera(float x, float y, float rotation, int mapId, float screenX, float screenY, float width, float height, float screenRotation, String name) {
		this(x, y, rotation, mapId, screenX, screenY, width, height, screenRotation);
		this.name = name;
	}
	
	/**
	 * @return Position of the camera in the game world
	 */
	public Vector2f getPosition() {
		return position;
	}
	
	/**
	 * @param position Position of the camera in the game world
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	/**
	 * @return Rotation of the camera relative to the game world (in degrees)
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation Rotation of the camera relative to the game world (in degrees)
	 */
	public void setRotation(float rotation) {
		this.rotation = (rotation + 360) % 360;
	}
	
	/**
	 * @return Id of the map at which the camera is looking
	 */
	public int getMapId() {
		return mapId;
	}
	
	/**
	 * @param mapId Id of the map at which the camera is looking
	 */
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	
	/**
	 * @return Size of the viewport on the screen
	 */
	public Vector2f getScreenSize() {
		return screenSize;
	}

	/**
	 * @param screen Size of the viewport on the screen
	 */
	public void setScreenSize(Vector2f screenSize) {
		this.screenSize = screenSize;
	}

	/**
	 * @return Position of the viewport on the screen
	 */
	public Vector2f getScreenPosition() {
		return screenPosition;
	}
	
	/**
	 * @param position Position of the viewport on the screen
	 */
	public void setScreenPosition(Vector2f screenPosition) {
		this.screenPosition = screenPosition;
	}
	
	/**
	 * @return Rotation of the viewport on the screen (in degrees)
	 */
	public float getScreenRotation() {
		return screenRotation;
	}

	/**
	 * @param rotation Rotation of the viewport on the screen (in degrees)
	 */
	public void setScreenRotation(float screenRotation) {
		this.screenRotation = (screenRotation + 360) % 360;
	}
	
	/**
	 * @return name of the camera
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Name of the camera
	 */
	public void setName(String name) {
		this.name = name;
	}
}
