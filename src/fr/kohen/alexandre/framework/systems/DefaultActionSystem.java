package fr.kohen.alexandre.framework.systems;

import java.util.HashMap;
import java.util.Map;

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
import fr.kohen.alexandre.framework.model.Action;
import fr.kohen.alexandre.framework.systems.interfaces.ActionsSystem;
import fr.kohen.alexandre.framework.systems.interfaces.PhysicsSystem;

/**
 * Updates the animations
 * @author Alexandre
 */
public class DefaultActionSystem extends EntityProcessingSystem implements ActionsSystem, ContactListener {
	protected ComponentMapper<ActionsComponent> actionsMapper;
	private PhysicsSystem 						box2dSystem;
	private Map<Entity, Action>					actions 			= new HashMap<Entity, Action>();
	private Map<String, Action> 				availableActions 	= new HashMap<String, Action>();

	@SuppressWarnings("unchecked")
	public DefaultActionSystem() {
		super( Aspect.getAspectForAll(ActionsComponent.class) );
	}
	
	public DefaultActionSystem( Map<String, Action> newActions ) {
		this();
		availableActions.putAll(newActions);
	}

	@Override
	public void initialize() {
		actionsMapper 	= ComponentMapper.getFor(ActionsComponent.class, world);
		box2dSystem		= Systems.get(PhysicsSystem.class, world);
		
		box2dSystem.addContactListener(this);
	}

	
	@Override
	protected void inserted(Entity e) {
		if ( availableActions.containsKey(actionsMapper.get(e).type) ) {
			Action action = availableActions.get(actionsMapper.get(e).type);
			action.initialize(world);
			actions.put(e, action);
		} else {
			throw new RuntimeException("No action returned for " + actionsMapper.get(e).type);
		}
	}
	
	
	@Override
	protected void process(Entity e) {
		actions.get(e).process(e);
	}
	
	@Override
	public void beginContact(Contact contact) {
		if ( contact.isTouching() ) {
			if ( actionsMapper.has((Entity) contact.getFixtureA().getBody().getUserData()) ) {
				actions.get((Entity) contact.getFixtureA().getBody().getUserData()).beginContact(
						(Entity) contact.getFixtureA().getBody().getUserData(),
						(Entity) contact.getFixtureB().getBody().getUserData(),
						contact
					);
			}
			if ( actionsMapper.has((Entity) contact.getFixtureB().getBody().getUserData()) ) {
				actions.get((Entity) contact.getFixtureB().getBody().getUserData()).beginContact(
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
			actions.get((Entity) contact.getFixtureA().getBody().getUserData()).endContact(
					(Entity) contact.getFixtureA().getBody().getUserData(),
					(Entity) contact.getFixtureB().getBody().getUserData(),
					contact
				);
		}
		if ( actionsMapper.has((Entity) contact.getFixtureB().getBody().getUserData()) ) {
			actions.get((Entity) contact.getFixtureB().getBody().getUserData()).endContact(
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