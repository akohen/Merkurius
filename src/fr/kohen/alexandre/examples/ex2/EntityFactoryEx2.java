package fr.kohen.alexandre.examples.ex2;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.examples.ex2.visuals.*;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.physicsbodies.*;
import fr.kohen.alexandre.framework.visuals.*;

public class EntityFactoryEx2 extends EntityFactory {
	
	
	public static Entity addLogo(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new Logo()) );
		e.addComponent( new Velocity(1,1) );
		e.addComponent( new EntityState() );
		e.addToWorld();
		return e;
	}
	
	public static Entity addPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new BoxVisual(25, 25, Color.BLUE)) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(1,1) );
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
