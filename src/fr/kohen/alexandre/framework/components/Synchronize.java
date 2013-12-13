package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Used to identify entities to synchronize
 * @author Alexandre
 *
 */
public class Synchronize extends Component {
	protected int id = 0; // The synchronized entity id
	protected String type = null; // The template of the entity
	protected boolean active = false; // Whether this entity has master components (their data should be sent to other clients)
	
	/**
	 * Use on server
	 * @param template
	 * @param active whether the entity should send data 
	 */
	public Synchronize(String template, boolean active) { 
		this.type = template; 
		this.active = active;
	}

	/**
	 * Use on clients
	 * @param template
	 * @param id the entity syncId (the id on the server)
	 * @param active whether the entity should send data 
	 */
	public Synchronize(String template, boolean active, int id) { 
		this.type = template; 
		this.id = id;
		this.active = active;
	}
	
	public int getId() { return id; }
	public String getType() { return type; }
	public boolean isActive() {return active; }

	public void setId(int id) { this.id = id; }
	public void setType(String type) { this.type = type; }	
	public void setActive(boolean active) { this.active = active; }
}
