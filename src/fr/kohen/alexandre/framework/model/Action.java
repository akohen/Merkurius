package fr.kohen.alexandre.framework.model;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Contact;

public interface Action {
	
	public void process(Entity e);
	
	/**
	 * Reacts to a contact with another entity
	 * @param e this entity
	 * @param other the other entity
	 * @param contact the contact object
	 */
	public void beginContact(Entity e, Entity other, Contact contact);
	
	/**
	 * Reacts to a contact with another entity
	 * @param e this entity
	 * @param other the other entity
	 * @param contact the contact object
	 */
	public void endContact(Entity e, Entity other, Contact contact);
	
	public void initialize(World world);
}
