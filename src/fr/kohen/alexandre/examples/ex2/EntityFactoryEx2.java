package fr.kohen.alexandre.examples.ex2;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.examples.ex2.visuals.Logo;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;

public class EntityFactoryEx2 extends EntityFactory {
	
	
	public static Entity addLogo(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Visual(new Logo().getSprite()) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(1,1) );
		e.addComponent( new EntityState() );
		e.addToWorld();
		return e;
	}
}
