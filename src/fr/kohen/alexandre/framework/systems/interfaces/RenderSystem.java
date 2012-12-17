package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;

public interface RenderSystem {
	
	public void setCamera(Entity camera);
	public void defaultRender(Entity e);
	public void resetCamera();
}
