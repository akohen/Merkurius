package fr.kohen.alexandre.framework.systems.interfaces;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Entity;

public interface CollisionSystem {
	
	/**
	 * Checks if there's a collision between two entities
	 * @param e1 the moving object
	 * @param e2 the second object
	 * @param mov movement of the first object
	 * @return true if there is a collision, false otherwise
	 */
	public boolean checkCollision(Entity e1, Entity e2, Vector2f mov);
	
	
	/**
	 * Checks if there's a collision between two entities
	 * @param e1 the first object
	 * @param e2 the second object
	 * @return true if there is a collision, false otherwise
	 */
	public boolean checkCollision(Entity e1, Entity e2);
	
	
	/**
	 * Checks if the entities can collide
	 * @param e1
	 * @param e2
	 * @return
	 */
	public boolean causeCollision(Entity e1, Entity e2);
	
	/**
	 * Checks whether the second entity is contained in the first one
	 * @param e1
	 * @param e2
	 * @return
	 */
	public boolean isContained(Entity e1, Entity e2);
}
