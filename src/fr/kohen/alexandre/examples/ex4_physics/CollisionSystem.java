package fr.kohen.alexandre.examples.ex4_physics;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.IPhysicsSystem;

public class CollisionSystem extends VoidEntitySystem implements ContactListener {

	private IPhysicsSystem box2dSystem;

	@Override
	protected void processSystem() {
	}
	
	@Override
	public void initialize() {
		box2dSystem	= Systems.get(IPhysicsSystem.class, world);
		if ( box2dSystem != null ) {
			box2dSystem.addContactListener(this);
		}
	}

	@Override
	public void beginContact(Contact contact) {
		Gdx.app.log("CollisionSystem", "begin contact");
	}

	@Override
	public void endContact(Contact contact) {
		Gdx.app.log("CollisionSystem", "end contact");
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {	
	}

}
