package fr.kohen.alexandre.framework.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class PhysicsBody {
	public Body body = null;
	public abstract void initialize(World box2dworld);
}
