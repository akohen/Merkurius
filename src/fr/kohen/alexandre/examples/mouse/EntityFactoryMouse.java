package fr.kohen.alexandre.examples.mouse;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.physicsBodies.BallBody;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class EntityFactoryMouse extends EntityFactory {


	
	public static Entity addPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new BoxVisual(25, 25, Color.BLUE)) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(100,100) );
		e.addComponent( new EntityState() );
		e.addComponent( new PhysicsBodyComponent(new BallBody()) );
		e.addComponent( new ActionsComponent(new ExampleAction(world)) );
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
