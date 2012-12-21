package fr.kohen.alexandre.framework;


import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;
import fr.kohen.alexandre.framework.spatials.TextSpatial;

/**
 * Methods for creating entities
 * @author Alexandre
 *
 */
public class EntityFactory {

	/**
	 * Create a new camera
	 * @param world
	 * @param mapId Id of the map on which the camera should be placed
	 * @param x camera X position in the game world
	 * @param y camera Y position in the game world
	 * @param rotation camera rotation in the game world
	 * @param screenX viewport center X position on the screen
	 * @param screenY viewport center Y position on the screen
	 * @param width viewport width
	 * @param height viewport height
	 * @param screenRotation viewport rotation
	 * @param name Camera name
	 * @return
	 */
	public static Entity createCamera(World world, int mapId, float x, float y, float rotation, int screenX, int screenY, int width, int height, float screenRotation, String name) {
		Entity e = world.createEntity();
		e.setTag(name);
		e.setGroup("CAMERA");
		e.addComponent(new Transform(mapId, x, y, rotation));
		e.addComponent(new Camera(screenX, screenY, width, height, screenRotation, name));
		e.refresh();
		return e;
	}
	
	public static Entity createTimer(World world, String tag, int duration) {
		Entity e = world.createEntity();
		e.setTag(tag);
		e.addComponent(new Expires(duration));
		e.refresh();
		return e;
	}
	
	public static Entity createObstacle(World world, int mapId, float x, float y, int width, int height) {
		Entity e = world.createEntity();
		e.setGroup("SOLID");
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new HitboxForm(new BoxSpatial(width, height), "solid"));
		e.refresh();
		return e;
	}
	
	
	public static Entity createBox(World world, int mapId, float x, float y, int size) {
		Entity e = world.createEntity();
		e.setGroup("SOLID");
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new SpatialForm(new BoxSpatial(size, size)));
		e.addComponent(new HitboxForm(new BoxSpatial(size, size), "solid"));
		e.refresh();
		return e;
	}
	
	
	
	public static Entity createItem(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new SpatialForm(new BoxSpatial(10, 10)));
		e.addComponent(new HitboxForm(new BoxSpatial(10, 10), "item"));
		e.addComponent(new Interactable(false, "item", 1));
		e.refresh();
		return e;
	}
	
	
	
	public static Entity createTeleport(World world, int mapId, float x, float y, int width, int height, int destMapId, int destX, int destY) {
		Entity e = world.createEntity();
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new SpatialForm(new BoxSpatial(width, height)));
		e.addComponent(new HitboxForm(new BoxSpatial(width, height), "effect"));
		e.addComponent(new Interactable(true, "teleport", destMapId, destX, destY));
		e.refresh();
		return e;
	}
	
	public static Entity createScript(World world, int mapId, float x, float y, int width, int height, String script) {
		Entity e = world.createEntity();
		e.addComponent(new Transform(mapId, x, y));
		//e.addComponent(new SpatialForm("Square", width, height));
		e.addComponent(new HitboxForm(new BoxSpatial(width, height), "effect"));
		e.addComponent(new Interactable(false, "script", script));
		e.refresh();
		return e;
	}
	
	
	public static Entity createMap(World world, int mapId, String mapName) {
		Entity e = world.createEntity();
		e.setGroup("MAP");
		e.setTag("map_"+mapId);
		e.addComponent(new Map(mapId, mapName));
		e.refresh();
		return e;
	}
	
	
	public static Entity createMap(World world, int mapId, int width, int height) {
		Entity e = world.createEntity();
		e.setGroup("MAP");
		e.setTag("map_"+mapId);
		e.addComponent(new Map(mapId, width, height));
		e.refresh();
		return e;
	}
	
	
	public static Entity createMouse(World world, Transform transform, Entity camera) {
		Entity e = world.createEntity();
		e.setTag("mouse");
		e.addComponent(transform);
		e.addComponent(new SpatialForm(new BoxSpatial(10, 10)));
		e.addComponent(new HitboxForm(new BoxSpatial(1, 1), "mouse"));
		e.addComponent(new Mouse(camera));
		e.refresh();
		return e;
	}
	
	
	public static Entity createInteractionMarker(World world, int parentId, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new HitboxForm(new BoxSpatial(10, 10), "effect"));
		e.addComponent(new SpatialForm(new BoxSpatial(10, 10)));
		e.addComponent(new Parent(parentId));
		e.refresh();
		return e;
	}
	
	
	public static Entity createButton(World world, float x, float y, int imgDef, int imgSel, String group, String action) {
		Entity e = world.createEntity();
		e.addComponent(new Transform(x,y));
		e.addComponent(new Button(imgDef, imgSel, group, action));
		e.refresh();
		return e;
	}
	
	
	public static Entity createButton(World world, float x, float y, String text, String group, String action) {
		Entity e = world.createEntity();
		e.addComponent(new Transform(x,y));
		e.addComponent(new Button(text, group, action));
		e.refresh();
		return e;
	}
	
	
	public static Entity createGuiText(World world, float x, float y, int width, String text) {
		Entity e = createGuiText(world, x, y, text);
		e.getComponent(SpatialForm.class).getSpatial().setLineWidth(width);
		return e;
	}
	
	public static Entity createGuiText(World world, float x, float y, String text) {
		Entity e = world.createEntity();
		e.setGroup("GUI");
		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm(new TextSpatial(text)));
		e.refresh();
		return e;
	}
	
	public static Entity createGuiText(World world, float x, float y, String text, int delay) {
		Entity e = createGuiText(world, x, y, text);
		e.setGroup("GUI");
		e.addComponent(new Expires(delay));
		e.refresh();
		return e;
	}
	
	
}