package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.engine.Spatial;

/**
 * Graphics
 * @author Alexandre
 *
 */
public class SpatialForm extends Component {
	private Spatial spatial;

	public SpatialForm(Spatial spatial) {
		setSpatial(spatial);
	}

	public Spatial getSpatial() {
		return spatial;
	}
	
	public void setSpatial(Spatial spatial) {
		this.spatial = spatial;
		this.spatial.initalize();
	}
}
