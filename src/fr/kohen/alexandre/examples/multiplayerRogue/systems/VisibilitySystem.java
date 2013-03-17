package fr.kohen.alexandre.examples.multiplayerRogue.systems;

import com.artemis.Entity;

public interface VisibilitySystem {
	public boolean hasLineOfSight(Entity e1, Entity e2);
}
