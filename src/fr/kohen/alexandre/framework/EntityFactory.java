package fr.kohen.alexandre.framework;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

import fr.kohen.alexandre.framework.components.*;

public class EntityFactory {
	
	public static Entity addCamera(World world, int mapId, float x, float y, float rotation, int screenX, int screenY, int width, int height, float screenRotation, String name) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register(name, e);
		world.getManager(GroupManager.class).add(e, "CAMERA");
		e.addComponent(new Transform(mapId, x, y, 1, rotation));
		e.addComponent(new CameraComponent(width, height, screenX, screenY, 1.0f, screenRotation, name));
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
