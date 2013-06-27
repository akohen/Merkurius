package fr.kohen.alexandre.wip.spacePhysics;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.*;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class EntityFactorySpacePhysics extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
	
	static {
		visuals.put( "box_10", new BoxVisual(10, 10, Color.RED) );
	}

	public static Entity newPlanet(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity() );
		e.addComponent( new VisualComponent("box_10") );
		e.addComponent( new PhysicsBodyComponent(new PlanetBody()) );
		e.addComponent( new EntityState() );
		e.addComponent( new GravityComponent() );
		return e;		
	}
	
	
	public static Entity newSatellite(World world, int mapId, float x, float y, float speed) {
		Velocity velocity = new Velocity();
		velocity.setSpeed(speed, 0);
		
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( velocity );
		e.addComponent( new VisualComponent("box_10") );
		e.addComponent( new PhysicsBodyComponent(new SatelliteBody()) );
		e.addComponent( new EntityState() );
		e.addComponent( new GravityComponent() );
		return e;		
	}
	

}
