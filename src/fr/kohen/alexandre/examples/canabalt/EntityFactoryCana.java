package fr.kohen.alexandre.examples.canabalt;

import org.newdawn.slick.Color;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;

/**
 * Methods for creating entities
 * @author Alexandre
 *
 */
public class EntityFactoryCana extends EntityFactory {
	
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
		e.addComponent(new Velocity(10f,10f));
		
		// Physical
		SpatialForm spatial = new SpatialForm(new BoxSpatial(50, 30));
		spatial.getSpatial().setColor(Color.white);
		e.addComponent(spatial);
		e.addComponent(new HitboxForm(new BoxSpatial(50, 30), "actor"));
		
		// Technical
		e.addComponent(new Player());
		e.addComponent(new EntityState());
		
		e.refresh();
		return e;
	}
	
	
}
