package fr.kohen.alexandre.tests.jeu1412;

import org.newdawn.slick.Color;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;

/**
 * Methods for creating entities
 * @author Alexandre
 *
 */
public class EntityFactory1412 extends EntityFactory {
	
	/**
	 * Creates a player entity
	 * @param world
	 * @param mapId
	 * @param x
	 * @param y
	 * @return
	 */
	public static Entity createPlayer(World world, int mapId, float x, float y, int rotation) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		world.getManager(GroupManager.class).add(e, "ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y, rotation));
		e.addComponent(new Velocity(10f,10f));
		
		// Physical
		SpatialForm spatial = new SpatialForm(new BoxSpatial(50, 30));
		spatial.getSpatial().setColor(Color.white);
		e.addComponent(spatial);
		e.addComponent(new HitboxForm(new BoxSpatial(50, 30), "actor"));
		
		// Technical
		e.addComponent(new Player());
		e.addComponent(new EntityState());
		
		e.addToWorld();
		return e;
	}
	
	
}
