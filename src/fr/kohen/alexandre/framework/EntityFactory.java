package fr.kohen.alexandre.framework;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.components.*;

public class EntityFactory {
	
	
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
