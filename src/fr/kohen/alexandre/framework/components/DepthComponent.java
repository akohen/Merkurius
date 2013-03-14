package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Holds the depth of the entity
 * @author Alexandre
 *
 */
public class DepthComponent extends Component {
	public int depth = 0;
	
	public DepthComponent() {
	}
	
	public DepthComponent(int depth) {
		this.depth = depth;
	}
}
