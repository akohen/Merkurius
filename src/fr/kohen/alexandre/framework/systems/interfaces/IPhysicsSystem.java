package fr.kohen.alexandre.framework.systems.interfaces;

import java.util.Hashtable;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;


public interface IPhysicsSystem extends ContactListener {
	
	public Hashtable<Integer, World> getUniverse();
	public void addContactListener(ContactListener listener);
	public void addContactListener(ContactListener listener, int mapId);
}
