package fr.kohen.alexandre.framework.systems.interfaces;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Entity;

public interface InteractionSystem {

	
	/**
	 * Adds an interaction marker at the designated location
	 * @param parent
	 * @param location
	 */
	public void addMarker(Entity parent, Vector2f location);
	
	

	
}
