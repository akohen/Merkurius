package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;
import com.artemis.utils.ImmutableBag;

public interface CameraSystem {
	ImmutableBag<Entity> getCameras();
	public void addToCamera(Entity camera, Entity entity);
	public void removeFromCamera(Entity camera, Entity entity);
}
