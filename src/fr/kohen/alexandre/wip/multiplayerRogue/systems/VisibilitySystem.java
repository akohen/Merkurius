package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import com.artemis.Entity;

public interface VisibilitySystem {
	public boolean hasLineOfSight(Entity e1, Entity e2);
}
