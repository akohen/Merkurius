package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;

public interface VisualDrawSystem extends DrawSystem {
	public void setVisual(Entity e, String visualName);
}
