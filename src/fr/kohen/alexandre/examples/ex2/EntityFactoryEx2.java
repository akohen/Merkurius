package fr.kohen.alexandre.examples.ex2;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.examples.ex2.visuals.*;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;

public class EntityFactoryEx2 extends EntityFactory {
	
	
	public static Entity addLogo(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new testVisual1()) );
		//e.addComponent( new VisualComponent(new Logo()) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(1,1) );
		e.addComponent( new EntityState() );
		e.addToWorld();
		return e;
	}

	public static Entity createBox(World world, int mapId, int x, int y, int l) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent(new testVisual1()) );
		//e.addComponent( new VisualComponent(new Logo()) );
		e.addComponent( new Player() );
		e.addComponent( new Velocity(1,1) );
		e.addComponent( new EntityState() );
		e.addToWorld();
		return e;		
	}
}
