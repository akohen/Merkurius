package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Used to identify the mouse
 * @author Alexandre
 *
 */
public class Mouse extends Component {
	protected Entity camera;

	public Mouse(Entity camera) { this.camera = camera; }
	
	public Entity getCamera() {
		return camera;
	}

	public void setCamera(Entity camera) {
		this.camera = camera;
	}

}
