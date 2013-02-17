package fr.kohen.alexandre.examples.ex3_network;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.examples.ex3_network.visuals.*;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.physicsbodies.*;
import fr.kohen.alexandre.framework.visuals.*;

public class EntityFactoryEx3 extends EntityFactory {
	

	public static Entity addLordLard(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(1,1) );
		
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
		e.addComponent( new Velocity(1,1) );
		
		e.addComponent( new VisualComponent(new BoxVisual(25, 25, Color.BLUE)) );
		e.addComponent( new PhysicsBodyComponent(new BallBody()) );
		
		e.addComponent( new Player() );
		e.addComponent( new EntityState() );
		e.addComponent( new Synchronize("player") );
		
		e.addToWorld();
		return e;
	}
	
	
	public static Entity addPlayerClient(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(1,1) );
		
		e.addComponent( new VisualComponent(new BoxVisual(25, 25, Color.BLUE)) );
		e.addComponent( new PhysicsBodyComponent(new BallBody()) );
		
		e.addComponent( new EntityState() );
		e.addComponent( new Synchronize("player") );
		
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
