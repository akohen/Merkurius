package fr.kohen.alexandre.framework.systems;

import fr.kohen.alexandre.framework.Systems;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.ICameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.IPhysicsSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class CameraSystem extends EntityProcessingSystem implements ICameraSystem, ContactListener {
	protected ComponentMapper<CameraComponent> 	cameraMapper;
	protected ComponentMapper<Transform> 		transformMapper;
	protected ImmutableBag<Entity>				cameras;
	protected IPhysicsSystem 					box2dSystem;

	@SuppressWarnings("unchecked")
	public CameraSystem() {
		super( Aspect.getAspectForAll(CameraComponent.class) );
		cameras			= new Bag<Entity>();
	}

	@Override
	public void initialize() {
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		box2dSystem		= Systems.get(IPhysicsSystem.class, world);
		
		box2dSystem.addContactListener(this);
	}
	
	@Override
	protected void begin() {
	}


	@Override
	protected void process(Entity camera) { 
		
		
		if ( box2dSystem != null ) {
			
		}
		
		if( cameraMapper.get(camera).name.startsWith("cameraFollowPlayer") ) {
			Entity player = world.getManager(TagManager.class).getEntity("player");
			if( player != null ) {
				transformMapper.get(camera).mapId = transformMapper.get(player).mapId;
				transformMapper.get(camera).setLocation( transformMapper.get(player).getLocation() );
			}
		} else if( cameraMapper.get(camera).name.startsWith("cameraRotationTest") ) {
			//transformMapper.get(camera).rotation = 0;
			//cameraMapper.get(camera).position.x = 0;
			//cameraMapper.get(camera).position.y = 0;
			cameraMapper.get(camera).rotation += 1;
		}
		
	}

	protected void inserted(Entity e) { ((Bag<Entity>) cameras).add(e); };
	protected void removed(Entity e) { ((Bag<Entity>) cameras).remove(e); };
	
	@Override
	public ImmutableBag<Entity> getCameras() { return cameras; }
	
	
	@Override
	public boolean isVisible(Entity e, Entity camera) {
		return isVisible(transformMapper.get(e), camera);
	}
	
	
	@Override
	public boolean isVisible(Transform transform, Entity camera) {
		if( transformMapper.get(camera).mapId == transform.mapId ) {			
			return true;
		}
		else return false;
	}

	private void addToCamera(Fixture cameraFixture, Fixture entityFixture) {	
		cameraMapper.get( (Entity) cameraFixture.getBody().getUserData() )
			.entities.add((Entity) entityFixture.getBody().getUserData());
	}
	private void removeFromCamera(Fixture cameraFixture, Fixture entityFixture) {	
		cameraMapper.get( (Entity) cameraFixture.getBody().getUserData() )
			.entities.remove((Entity) entityFixture.getBody().getUserData());
	}
	
	
	@Override
	public void beginContact(Contact contact) {
		if ( contact.getFixtureA().getUserData() instanceof String && ((String) contact.getFixtureA().getUserData()).equalsIgnoreCase("cameraSensor") ) {
			addToCamera( contact.getFixtureA(),contact.getFixtureB() );
		}
		if ( contact.getFixtureB().getUserData() instanceof String && ((String) contact.getFixtureB().getUserData()).equalsIgnoreCase("cameraSensor") ) {
			addToCamera( contact.getFixtureB(),contact.getFixtureA() );
		}
	}

	@Override
	public void endContact(Contact contact) {
		if ( contact.getFixtureA().getUserData() instanceof String && ((String) contact.getFixtureA().getUserData()).equalsIgnoreCase("cameraSensor") ) {
			removeFromCamera( contact.getFixtureA(),contact.getFixtureB() );
		}
		if ( contact.getFixtureB().getUserData() instanceof String && ((String) contact.getFixtureB().getUserData()).equalsIgnoreCase("cameraSensor") ) {
			removeFromCamera( contact.getFixtureB(),contact.getFixtureA() );
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {	
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {	
	}

}
