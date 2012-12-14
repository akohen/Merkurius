package fr.kohen.alexandre.examples.miniRPG;


import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.examples.miniRPG.hitboxes.LordLardHitbox;
import fr.kohen.alexandre.examples.miniRPG.spatials.LordLard;
import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;

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
		e.setTag("player");
		e.setGroup("ACTOR");
		
		// Position and movement
		e.addComponent(new Transform(mapId, x, y));
		e.addComponent(new Velocity(5f,5f));
		
		// Physical
		e.addComponent(new SpatialForm(new LordLard()));
		e.addComponent(new HitboxForm(new LordLardHitbox(), "actor"));
		
		// Technical
		e.addComponent(new Player());
		e.addComponent(new EntityState());
		
		e.refresh();
		return e;
	}
	
}
