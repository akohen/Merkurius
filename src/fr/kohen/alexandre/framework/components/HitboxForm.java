package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;

import fr.kohen.alexandre.framework.engine.Spatial;

/**
 * Gives the entity a hitbox
 * @author Alexandre
 *
 */
public class HitboxForm extends Component {
	//private Hitbox hitbox;
	private String type;

	private Spatial spatial;

	public HitboxForm(Spatial spatial, String type) {
		setSpatial(spatial);
		setType(type);
	}

	public void setSpatial(Spatial spatial) {
		this.spatial = spatial;
		this.spatial.initalize();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the shape at the specified location
	 * @param loc
	 * @param angle in radians
	 * @return
	 */
	public Shape getShape(Vector2f loc, float angle) {
		return this.spatial.getShape(loc, angle);
	}
	
	public Shape getShape() {
		return this.spatial.getShape();
	}
	
	public Spatial getSpatial() {
		return this.spatial;
	}
	
}
