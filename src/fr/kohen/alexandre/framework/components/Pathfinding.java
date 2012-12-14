package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;

import com.artemis.Component;

public class Pathfinding extends Component {
	private Vector2f destination;
	private Path path;
	private int destinationMap;
	private int step;
	private boolean update;
	
	public Pathfinding(int mapId, float x, float y ) {
		this();
		this.destination 	= new Vector2f(x,y);
		this.destinationMap = mapId;
		update 				= true;
	}
	
	public Pathfinding() {
		path 	= null;
		update 	= false;
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f destination) {
		this.destination = destination;
		update = true;
	}
	
	/**
	 * Sets the destination
	 * @param mapId
	 * @param x
	 * @param y
	 */
	public void setDestination(int mapId, int x, int y) {
		this.destination 	= new Vector2f(x,y);
		this.destinationMap = mapId;
		update = true;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
		this.step = 1;
		update = false;
	}

	public int getDestinationMap() {
		return destinationMap;
	}

	public void setDestinationMap(int destinationMap) {
		this.destinationMap = destinationMap;
		update = true;
	}
	
	/**
	 * Returns true if the path needs to be updated
	 * @return
	 */
	public boolean getUpdate() {
		return update;
	}
	
	/**
	 * Returns the coordinates (in pixels) of the next step of the path
	 * @param tileDim
	 * @return
	 */
	public Vector2f getStep(Vector2f tileDim) {
		return new Vector2f( path.getX(step) * tileDim.x, path.getY(step) * tileDim.y );
	}
	
	/**
	 * Goes to the next step
	 */
	public void nextStep() {
		if( step < path.getLength() - 1 )
			this.step++;
		else
			this.path = null;
	}
	
	/**
	 * Removes this NPC destination
	 */
	public void removeDestination() {
		this.destinationMap = -1;
		this.destination = null;
		this.path = null;
	}

}
