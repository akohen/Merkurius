package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.ActionsComponent;
import fr.kohen.alexandre.framework.systems.interfaces.IActionsSystem;
import fr.kohen.alexandre.framework.systems.interfaces.IPhysicsSystem;

/**
 * Updates the animations
 * @author Alexandre
 */
public class ActionSystem extends EntityProcessingSystem implements IActionsSystem, ContactListener {
	protected ComponentMapper<ActionsComponent> actionsMapper;
	protected IPhysicsSystem box2dSystem;

	@SuppressWarnings("unchecked")
	public ActionSystem() {
		super( Aspect.getAspectForAll(ActionsComponent.class) );
	}

	@Override
	public void initialize() {
		actionsMapper 	= ComponentMapper.getFor(ActionsComponent.class, world);
		box2dSystem		= Systems.get(IPhysicsSystem.class, world);
		
		box2dSystem.addContactListener(this);
	}

	@Override
	protected void process(Entity e) {
		actionsMapper.get(e).action.process(e);
	}
	
	@Override
	public void beginContact(Contact contact) {
		if ( contact.isTouching() ) {
			if ( actionsMapper.has((Entity) contact.getFixtureA().getBody().getUserData()) ) {
				actionsMapper.get((Entity) contact.getFixtureA().getBody().getUserData()).action.beginContact(
						(Entity) contact.getFixtureA().getBody().getUserData(),
						(Entity) contact.getFixtureB().getBody().getUserData(),
						contact
					);
			}
			if ( actionsMapper.has((Entity) contact.getFixtureB().getBody().getUserData()) ) {
				actionsMapper.get((Entity) contact.getFixtureB().getBody().getUserData()).action.beginContact(
						(Entity) contact.getFixtureB().getBody().getUserData(),
						(Entity) contact.getFixtureA().getBody().getUserData(),
						contact
					);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		if ( actionsMapper.has((Entity) contact.getFixtureA().getBody().getUserData()) ) {
			actionsMapper.get((Entity) contact.getFixtureA().getBody().getUserData()).action.endContact(
					(Entity) contact.getFixtureA().getBody().getUserData(),
					(Entity) contact.getFixtureB().getBody().getUserData(),
					contact
				);
		}
		if ( actionsMapper.has((Entity) contact.getFixtureB().getBody().getUserData()) ) {
			actionsMapper.get((Entity) contact.getFixtureB().getBody().getUserData()).action.endContact(
					(Entity) contact.getFixtureB().getBody().getUserData(),
					(Entity) contact.getFixtureA().getBody().getUserData(),
					contact
				);
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}

}	