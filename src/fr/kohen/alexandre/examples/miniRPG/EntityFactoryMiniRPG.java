package fr.kohen.alexandre.examples.miniRPG;


import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

import fr.kohen.alexandre.examples.miniRPG.spatials.LordLard;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;

/**
 * Methods for creating entities
 * @author Alexandre
 *
 */
public class EntityFactoryMiniRPG extends EntityFactory {
	
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
		e.addComponent(new Velocity(5f,5f));
		
		// Physical
		e.addComponent(new SpatialForm(new LordLard()));
		e.addComponent(new HitboxForm(new BoxSpatial(16, 30), "actor"));
		
		// Technical
		e.addComponent(new Player());
		e.addComponent(new EntityState());
		
		e.addToWorld();
		return e;
	}
	
}
