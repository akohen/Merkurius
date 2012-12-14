package fr.kohen.alexandre.games.space;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.games.space.actions.ClientHeadingActions;
import fr.kohen.alexandre.games.space.actions.IncreaseHeadingActions;
import fr.kohen.alexandre.games.space.actions.TestActions;
import fr.kohen.alexandre.games.space.spatials.*;

/**
 * Methods for creating entities
 * @author Alexandre
 *
 */
public class EntityFactorySpaceGame extends EntityFactory {
	
	/**
	 * Creates a player entity
	 * @param world
	 * @param mapId
	 * @param x
	 * @param y
	 * @return
	 */
	public static Entity createPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.setTag("player");
		e.setGroup("ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new Destination(mapId, x, y));
		Velocity velocity = new Velocity(10f,10f);
		velocity.setMaxRotation(5f);
		e.addComponent(velocity);
		
		// Physical
		e.addComponent( new SpatialForm(new Ship()) );
		e.addComponent( new HitboxForm(new Ship(), "actor") );
		
		// Technical
		e.addComponent(new Player());
		e.addComponent(new EntityState());
		e.addComponent( new Synchronize("player") );
		e.addComponent( new Actions(new TestActions(world)) );
		
		e.refresh();
		return e;
	}
	
	public static Entity createPlayerClient(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.setTag("player");
		e.setGroup("ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new Destination(mapId, x, y));
		Velocity velocity = new Velocity(10f,10f);
		velocity.setMaxRotation(5f);
		e.addComponent(velocity);
		
		// Physical
		e.addComponent( new SpatialForm(new Ship()) );
		e.addComponent( new HitboxForm(new Ship(), "actor") );
		
		// Technical
		e.addComponent(new EntityState());
		e.addComponent( new Synchronize("player") );
		e.addComponent( new Actions(new TestActions(world)) );
		
		e.refresh();
		return e;
	}
	
	
	public static Entity createBox(World world, int mapId, float x, float y, int size) {
		Entity e = EntityFactory.createBox(world, mapId, x, y, size);
		e.addComponent( new Synchronize("box") );
		e.refresh();
		return e;
	}
	
	
	public static Entity createArrow(World world, int mapId, float x, float y, String direction) {
		Entity e = world.createEntity();
		e.setTag("gui_hdg_left");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));

		// Physical
		e.addComponent( new SpatialForm(new Arrow(direction)) );
		e.addComponent( new HitboxForm(new Arrow(direction), "gui") );
		
		// Technical
		e.addComponent(new EntityState());
		e.addComponent( new Actions(new ClientHeadingActions(world, direction)) );
		
		e.refresh();
		return e;
	}
	
	
	public static Entity createArrow(World world, int mapId, float x, float y, String direction, Entity player) {
		Entity e = world.createEntity();
		e.setTag("gui_hdg_left");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));

		// Physical
		e.addComponent( new SpatialForm(new Arrow(direction)) );
		e.addComponent( new HitboxForm(new Arrow(direction), "gui") );
		
		// Technical
		e.addComponent(new EntityState());
		e.addComponent( new Actions(new IncreaseHeadingActions(world, player, direction)) );
		
		e.refresh();
		return e;
	}
	
}
