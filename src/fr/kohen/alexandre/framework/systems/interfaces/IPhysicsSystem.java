package fr.kohen.alexandre.framework.systems.interfaces;

import java.util.Hashtable;

import com.badlogic.gdx.physics.box2d.World;


public interface IPhysicsSystem {
	
	public Hashtable<Integer, World> getUniverse();
}
