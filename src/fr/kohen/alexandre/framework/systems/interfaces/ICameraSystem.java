package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;
import com.artemis.utils.ImmutableBag;

import fr.kohen.alexandre.framework.components.Transform;

public interface ICameraSystem {
	boolean isVisible(Entity e, Entity camera);
	
	boolean isVisible(Transform transform, Entity camera);
	
	ImmutableBag<Entity> getCameras();
}
