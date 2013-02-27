package fr.kohen.alexandre.framework.base;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.physicsBodies.*;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class EntityFactory {
	
	public static Entity addCamera(World world, int mapId, float x, float y, float rotation, int screenX, int screenY, int width, int height, float screenRotation, String name) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register(name, e);
		world.getManager(GroupManager.class).add(e, "CAMERA");
		
		e.addComponent( new Transform(mapId, x, y, 1, rotation) );
		e.addComponent( new CameraComponent(width, height, screenX, screenY, 1.0f, screenRotation, name) );
		e.addComponent( new PhysicsBodyComponent(new CameraBody(width-1, height-1)) );
		
		e.addToWorld();
		return e;
	}
	
	public static Entity addMouse(World world, Transform transform, Entity camera) {
		Entity e = world.createEntity();
		world.getManager(GroupManager.class).add(e, "MOUSE");
		e.addComponent(transform);
		e.addComponent(new VisualComponent(new BoxVisual(10, 10, Color.DARK_GRAY)));
		e.addComponent(new PhysicsBodyComponent(new MouseBody()));
		e.addComponent(new Mouse(camera));
		e.addToWorld();
		return e;
	}
	
	public static Entity addPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(1,1) );
		e.addComponent( new EntityState() );
		e.addToWorld();
		return e;
	}
	
	
}
