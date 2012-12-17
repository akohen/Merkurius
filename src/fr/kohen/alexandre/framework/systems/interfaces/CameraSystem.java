package fr.kohen.alexandre.framework.systems.interfaces;

import java.util.List;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Spatial;

public interface CameraSystem {

	public List<Entity> getCameras();

	boolean isVisible(Entity e, Entity camera);
	
	boolean isVisible(Transform transform, Spatial spatial, Entity camera);
}
