package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;

/**
 * Interaction marker
 * @author Alexandre
 *
 */
public class Interactable extends Component {
	private boolean activated;
	private String 	type;
	private int[] 	args;
	private String 	script;
	
	/**
	 * @param activated true for started by activation, false for collision
	 * @param type the type of interaction "dialog", "teleport", "item", "activate", "inventory"
	 */
	public Interactable(boolean activated, String type, int...args) {
		this.activated = activated;
		this.type = type;
		this.args = args;
	}
	
	public Interactable(boolean activated, String type, String script) {
		this(activated, type);
		this.script = script;
	}
	
	/**
	 * Returns true is the interaction starts by activation, false for collision
	 * @return
	 */
	public boolean isActivated() { return activated;}
	
	/**
	 * Returns true if a dialog can be started
	 * @return
	 */
	public boolean canSpeak() { return hasType("dialog"); }
	
	/**
	 * Returns true if this is a teleporter
	 * @return
	 */
	public boolean canTeleport() { return hasType("teleport"); }
	
	/**
	 * Returns true if the entity can be picked up
	 * @return
	 */
	public boolean isItem() { return hasType("item"); }
	
	/**
	 * Returns true if the entity can be picked up
	 * @return
	 */
	public boolean hasInventory() { return hasType("inventory"); }
	
	/**
	 * Returns true if the entity can start a script
	 * @return
	 */
	public boolean isScript() { return hasType("script"); }
	
	
	public boolean hasType(String string) {
		if( type.indexOf(string) >-1 )
			return true;
		else 
			return false;
	}
	
	
	/**
	 * Teleporter location
	 * @return
	 */
	public Vector2f getTeleportLoc() { return new Vector2f(args[1], args[2]); }

	/**
	 * Teleporter map destination
	 * @return
	 */
	public int getTeleportMap() { return args[0]; }
	
	/**
	 * Id of the item
	 * @return id
	 */
	public int getItemType() { return args[0]; }
	
	/**
	 * Script to execute
	 * @return script
	 */
	public String getScript() { return script; }

}
