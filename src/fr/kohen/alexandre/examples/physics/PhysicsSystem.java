package fr.kohen.alexandre.examples.physics;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;

public class PhysicsSystem extends DefaultBox2DSystem {
	protected World newWorld(Entity e) {
		return new World(new Vector2(0, -10), true);
	}
}
