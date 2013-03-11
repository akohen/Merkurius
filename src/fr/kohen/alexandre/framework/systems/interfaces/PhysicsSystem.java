package fr.kohen.alexandre.framework.systems.interfaces;

import java.util.Hashtable;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;


public interface PhysicsSystem extends ContactListener {
	
	/**
	 * Return the universe ( = set of all the worlds)
	 * @return A table of worlds indexed by mapId
	 */
	public Hashtable<Integer, World> getUniverse();
	
	/**
	 * Add a new listener to all the worlds. The listener will be notified of contact events in all the worlds.
	 * @param listener
	 */
	public void addContactListener(ContactListener listener);
	
	/**
	 * Add a new listener to a single world. The listener will be notified of contact events in the specified world.
	 * @param listener
	 * @param mapId
	 */
	public void addContactListener(ContactListener listener, int mapId);
}
