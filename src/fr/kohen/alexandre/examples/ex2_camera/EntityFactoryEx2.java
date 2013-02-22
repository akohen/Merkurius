package fr.kohen.alexandre.examples.ex2_camera;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.examples.ex2_camera.visuals.*;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.physicsbodies.*;
import fr.kohen.alexandre.framework.visuals.*;

public class EntityFactoryEx2 extends EntityFactory {
	
	public static Entity testCamera(World world, int mapId, float x, float y, float rotation, int screenX, int screenY, int width, int height, float screenRotation, String name) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y, 1, rotation) );
		e.addComponent( new CameraComponent(width, height, screenX, screenY, 1.0f, screenRotation, name) );
		e.addComponent( new PhysicsBodyComponent(new CameraBody(150, 100)) );
		
		e.addToWorld();
		return e;
	}

	
	public static Entity addLordLard(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(100,100) );
		
		e.addComponent( new VisualComponent(new LordLardVisual()) );
		e.addComponent( new PhysicsBodyComponent(new LordLardBody()) );
		
		e.addComponent( new EntityState() );
		e.addComponent( new Player() );
		
		e.addToWorld();
		return e;
	}
	
	public static Entity addPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new BoxVisual(25, 25, Color.BLUE)) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(100,100) );
		e.addComponent( new EntityState() );
		e.addComponent( new PhysicsBodyComponent(new BallBody()) );
		e.addToWorld();
		return e;
	}

	public static Entity createBox(World world, int mapId, int x, int y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new BoxVisual(size, size, Color.RED)) );
		e.addComponent( new Velocity(1,1) );
		e.addComponent( new EntityState() );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(size)) );
		e.addToWorld();
		return e;		
	}
}
