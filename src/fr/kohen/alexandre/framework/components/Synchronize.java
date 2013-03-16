package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Used to identify entities to synchronize
 * @author Alexandre
 *
 */
public class Synchronize extends Component {
	protected int id;
	protected String type;
	
	public Synchronize() { }
	
	public Synchronize(int id) { this.id = id; }

	public Synchronize(String type) { this.type = type; }
	public Synchronize(String type, int id) { this.type = type; this.id = id;}
	
	public int getId() { return id; }
	public String getType() { return type; }

	public void setId(int id) { this.id = id; }
	public void setType(String type) { this.type = type; }
}
