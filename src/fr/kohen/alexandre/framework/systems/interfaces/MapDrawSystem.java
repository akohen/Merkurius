package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;


public interface MapDrawSystem extends DrawSystem {
	public Entity getMap(int mapId);
}
