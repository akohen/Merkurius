package fr.kohen.alexandre.framework.engine;

import com.artemis.Entity;
import com.artemis.World;


public abstract class ActionList {

	protected World world;

	public ActionList(World world) { this.world = world; }
	
	/**
	 * Actions when the entity is clicked
	 * @param e the affected entity
	 */
	public void onMouseClick(Entity e) { this.onActivate(e); }
	
	/**
	 * Actions when the mouse is over the entity
	 * @param e the affected entity
	 */
	public void onMouseHover(Entity e) { this.onSelect(e); }
	
	/**
	 * Actions when the mouse is not over the entity
	 * @param e the affected entity
	 */
	public void onMouseOff(Entity e) { this.onDeselect(e); }
	
	/**
	 * Actions when the mouse is clicked, but not over the entity
	 * @param e the affected entity
	 */
	public void onMouseOffClick(Entity e) { this.onDeselect(e); }
	
	/**
	 * Actions when the entity is activated
	 * @param e the affected entity
	 */
	public void onActivate(Entity e) { }
	
	/**
	 * Actions when the entity is selected
	 * @param e the affected entity
	 */
	public void onSelect(Entity e) { }
	
	/**
	 * Actions when the entity is deselected
	 * @param e the affected entity
	 */
	public void onDeselect(Entity e) { }
	
	
}
