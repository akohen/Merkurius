package fr.kohen.alexandre.examples._common;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;

import fr.kohen.alexandre.examples.mouse.ExampleAction;
import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.Action;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.physicsBodies.CameraBody;
import fr.kohen.alexandre.framework.model.physicsBodies.LordLardBody;

public class EntityFactoryExamples extends EntityFactory {
	
	
	/* Demonstrates how a single model class (like Action or Visual) can be used for multiple entities
	 * Note that the class state is shared between entities!
	 */
	private static Action action = null;
	private static Action getAction(World world) {
		if ( action == null ) { action = new ExampleAction(world); }
		return action;
	}
	
	public static Entity newPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(200,200) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
		e.addComponent( new Player() );
		e.addComponent( new EntityState() );
		return e;
	}

	public static Entity newBox(World world, int mapId, float x, float y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent("example_box_" + size) );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(size)) );
		e.addComponent( new EntityState() );
		return e;		
	}
	
	public static Entity newMap(World world, int mapId, String mapName) {
		Entity e = world.createEntity();
		e.addComponent( new MapComponent(mapId,mapName) );
		return e;
	}
	
	
	public static Entity testCamera(World world, int mapId, float x, float y, float rotation, int screenX, int screenY, int width, int height, float screenRotation, String name) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y, 1, rotation) );
		e.addComponent( new CameraComponent(width, height, screenX, screenY, 1.0f, screenRotation, name) );
		e.addComponent( new PhysicsBodyComponent(new CameraBody(150, 100)) );
		return e;
	}

	
	public static Entity newLordLard(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(100,100) );
		
		e.addComponent( new VisualComponent("lord_lard") );
		e.addComponent( new PhysicsBodyComponent(new LordLardBody()) );
		
		e.addComponent( new EntityState() );
		e.addComponent( new Player() );
		return e;
	}
	
	public static Entity newMouseExamplePlayer(World world, int mapId, float x, float y) {
		Entity e = newPlayer(world, mapId, x, y);
		e.addComponent( new ActionsComponent(getAction(world)) );
		return e;
	}

	public static Entity newMouseExampleBox(World world, int mapId, int x, int y, int size) {
		Entity e = newBox(world, mapId, x, y, size);
		e.addComponent( new ActionsComponent(getAction(world)) );
		return e;		
	}
	
	public static Entity newNetworkExamplePlayer(World world, int mapId, float x, float y) {
		Entity e = newPlayer(world, mapId, x, y);
		e.addComponent( new ActionsComponent(getAction(world)) );
		e.addComponent( new Synchronize("player") );
		return e;
	}
	
	public static Entity newNetworkExamplePlayerClient(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(100,100) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
		e.addComponent( new Player() );
		e.addComponent( new EntityState() );
		e.addComponent( new Synchronize("player") );
		return e;
	}
}
