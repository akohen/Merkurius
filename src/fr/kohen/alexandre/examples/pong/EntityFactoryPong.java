package fr.kohen.alexandre.examples.pong;

import org.newdawn.slick.Color;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

import fr.kohen.alexandre.examples.pong.components.Ball;
import fr.kohen.alexandre.examples.pong.components.Enemy;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;
import fr.kohen.alexandre.framework.spatials.CircleSpatial;

/**
 * Methods for creating entities
 * @author Alexandre
 *
 */
public class EntityFactoryPong extends EntityFactory {
	
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
		world.getManager(TagManager.class).register("player", e);
		world.getManager(GroupManager.class).add(e, "ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new Velocity(10f,10f));
		
		// Physical
		SpatialForm spatial = new SpatialForm(new BoxSpatial(15, 100));
		spatial.getSpatial().setColor(Color.white);
		e.addComponent(spatial);
		e.addComponent(new HitboxForm(new BoxSpatial(15, 100), "actor"));
		
		// Technical
		e.addComponent(new Player());
		e.addComponent(new EntityState());
		
		e.addToWorld();
		return e;
	}
	
	
	public static Entity createEnemy(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("enemy", e);
		world.getManager(GroupManager.class).add(e, "ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new Velocity(10f,10f));
		
		// Physical
		SpatialForm spatial = new SpatialForm(new BoxSpatial(15, 100));
		spatial.getSpatial().setColor(Color.white);
		e.addComponent(spatial);
		e.addComponent(new HitboxForm(new BoxSpatial(15, 100), "actor"));
		
		// Technical
		e.addComponent(new Enemy());
		e.addComponent(new EntityState());
		
		e.addToWorld();
		return e;
	}
	
	
	public static Entity createBall(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("ball", e);
		world.getManager(GroupManager.class).add(e, "ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));
		
		Velocity velocity = new Velocity(5f,8f);
		e.addComponent(velocity);
		
		// Physical
		SpatialForm spatial = new SpatialForm(new CircleSpatial(10));
		spatial.getSpatial().setColor(Color.white);
		e.addComponent(spatial);
		e.addComponent(new HitboxForm(new BoxSpatial(20, 20), "solid"));
		
		// Technical
		e.addComponent(new Ball());
		e.addComponent(new EntityState());
		
		e.addToWorld();
		return e;
	}
}
