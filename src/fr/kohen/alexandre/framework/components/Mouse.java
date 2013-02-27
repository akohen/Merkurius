package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.artemis.Entity;

/**
 * Used to identify the mouse
 * @author Alexandre
 *
 */
public class Mouse extends Component {
	public Entity camera;
	public boolean clicked = false;

	public Mouse(Entity camera) { this.camera = camera; }
}
