package fr.kohen.alexandre.framework.systems;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.systems.interfaces.PhysicsSystem;

/**
 * Updates the entities position according to the velocity.
 * This system must me called after all other systems affecting the entities position (such as control or collision systems for example)
 * 
 * @author Alexandre
 */
public class DefaultBox2DSystem extends EntityProcessingSystem implements PhysicsSystem {
	protected ComponentMapper<Transform> 				transformMapper;
	protected ComponentMapper<Velocity> 				velocityMapper;
	protected ComponentMapper<PhysicsBodyComponent> 	bodyMapper;
	
	private Hashtable<Integer, World> 					universe;
	private Hashtable<Integer, List<ContactListener>> 	contactListeners;
	private List<ContactListener> 						globalListeners;
	private Hashtable<Entity, Integer> 					previousMap;

	@SuppressWarnings("unchecked")
	public DefaultBox2DSystem() {
		super( Aspect.getAspectForAll(Transform.class, PhysicsBodyComponent.class) );
		universe		= new Hashtable<Integer,World>();
		contactListeners= new Hashtable<Integer, List<ContactListener>>();
		globalListeners	= new ArrayList<ContactListener>();
		previousMap		= new Hashtable<Entity, Integer>();
	}

	@Override
	protected void initialize() {
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
	}
	
	
	@Override
	protected void inserted(Entity e) {
		World b2World;
		
		// Adding to box2D world
		if( universe.containsKey(transformMapper.get(e).mapId) ) {
			b2World = universe.get(transformMapper.get(e).mapId);
		} else {
			b2World = newWorld(e);
			b2World.setContactListener(this);
			universe.put( transformMapper.get(e).mapId, b2World );
		}
		
		// Adding contact listeners
		if( !contactListeners.containsKey(transformMapper.get(e).mapId) ) {
			contactListeners.put( transformMapper.get(e).mapId, new ArrayList<ContactListener>() );
		}
		
		// Body initialization
		bodyMapper.get(e).physicsBody.initialize( b2World );
		bodyMapper.get(e).getBody().setUserData(e);
		previousMap.put(e, transformMapper.get(e).mapId);
		updateBody(e);
	}
	
	
	@Override
	protected void removed(Entity e) {
		World b2world = bodyMapper.get(e).getBody().getWorld();
		b2world.destroyBody( bodyMapper.get(e).getBody() ); // Removing body from world
		if( b2world.getBodyCount() == 0 ) { // Removing empty world
			universe.remove( previousMap.get(e) );
			b2world.dispose();
		}
		previousMap.remove(e);
	}
	
	
	@Override
	protected void process(Entity e) {
		if ( e.isEnabled() ) {
			bodyMapper.get(e).getBody().setAwake(true); // Make sure the body is processed
			updateBody(e);
		} else {
			bodyMapper.get(e).getBody().setAwake(false); // Inactive entities should not be processed
		}
	}
	
	public void raycast(int worldId, RayCastCallback callback, Vector2 point1, Vector2 point2) {
		universe.get(worldId).rayCast(callback, point1, point2);
	}
	
	
	protected void updateBody(Entity e) {
		// Updating position
		bodyMapper.get(e).getBody().setTransform( 
				transformMapper.get(e).getPosition2(), 
				transformMapper.get(e).rotation * MathUtils.degreesToRadians 
			);
		
		// Updating speed
		if( velocityMapper.has(e) ) { 
			bodyMapper.get(e).getBody().setLinearVelocity(velocityMapper.get(e).speed);
			bodyMapper.get(e).getBody().setAngularVelocity(velocityMapper.get(e).getRotation());			
		} 
		
		// Switching world
		if ( previousMap.get(e) != transformMapper.get(e).mapId ) { 
			removed(e);
			inserted(e);
		}
	}

	
	protected void end() {
		updateWorlds();	
		updateEntitiesAfterWorldUpdate();
	}


	private void updateWorlds() {
		for( Integer i : universe.keySet() ) {
			World b2World = universe.get(i);
			b2World.step( world.getDelta(), 6, 2);
		}	
	}
	
	
	private void updateEntitiesAfterWorldUpdate() {
		// Updating transform component
		for (int i = 0, s = getActives().size(); s > i; i++) {
			Entity e = getActives().get(i);
			transformMapper.get(e).setPosition(bodyMapper.get(e).getBody().getPosition());
			transformMapper.get(e).rotation = bodyMapper.get(e).getBody().getAngle() * MathUtils.radiansToDegrees;
			if( velocityMapper.getSafe(e) != null ) { // adding speed
				velocityMapper.get(e).speed = bodyMapper.get(e).getBody().getLinearVelocity();
				velocityMapper.get(e).rotation = bodyMapper.get(e).getBody().getAngularVelocity();		
			}
		}
	}
	
	
	/**
	 * Return a new world to be added to the universe. Use this function to change default world behavior
	 * @param e The entity causing the world creation
	 * @return
	 */
	protected World newWorld(Entity e) {
		return new World(new Vector2(0, 0), true);
	}


	@Override
	public Hashtable<Integer, World> getUniverse() {
		return universe;
	}
	
	
	public void addContactListener(ContactListener listener) {
		globalListeners.add(listener);
	}
	
	
	public void addContactListener(ContactListener listener, int mapId) {
		if( !contactListeners.containsKey(mapId) ) {
			contactListeners.put( mapId, new ArrayList<ContactListener>() );
		}
		contactListeners.get(mapId).add(listener);
	}

	
	// Contact listener
	@Override
	public void beginContact(Contact contact) {
		int mapId = transformMapper.get((Entity) contact.getFixtureA().getBody().getUserData()).mapId;
		for ( ContactListener listener : contactListeners.get(mapId)  ) { listener.beginContact(contact); }
		for ( ContactListener listener : globalListeners  ) { listener.beginContact(contact); }
	}

	
	@Override
	public void endContact(Contact contact) {
		int mapId = transformMapper.get((Entity) contact.getFixtureA().getBody().getUserData()).mapId;
		for ( ContactListener listener : contactListeners.get(mapId)  ) { listener.endContact(contact); }
		for ( ContactListener listener : globalListeners  ) { listener.endContact(contact); }
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		int mapId = transformMapper.get((Entity) contact.getFixtureA().getBody().getUserData()).mapId;
		for ( ContactListener listener : contactListeners.get(mapId)  ) { listener.preSolve(contact, oldManifold); }
		for ( ContactListener listener : globalListeners  ) { listener.preSolve(contact, oldManifold); }
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		int mapId = transformMapper.get((Entity) contact.getFixtureA().getBody().getUserData()).mapId;
		for ( ContactListener listener : contactListeners.get(mapId)  ) { listener.postSolve(contact, impulse); }
		for ( ContactListener listener : globalListeners  ) { listener.postSolve(contact, impulse); }
	}

	
	
}	