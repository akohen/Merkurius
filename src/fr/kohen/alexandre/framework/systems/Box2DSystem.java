package fr.kohen.alexandre.framework.systems;

import java.util.Hashtable;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;

/**
 * Updates the entities position according to the velocity.
 * This system must me called after all other systems affecting the entities position (such as control or collision systems for example)
 * 
 * @author Alexandre
 */
public class Box2DSystem extends EntityProcessingSystem {
	protected ComponentMapper<Transform> 			transformMapper;
	protected ComponentMapper<Velocity> 			velocityMapper;
	protected ComponentMapper<CameraComponent> 		cameraMapper;
	protected ComponentMapper<PhysicsBodyComponent> bodyMapper;
	protected Box2DDebugRenderer 					debugRenderer;
	private Hashtable<Integer, World> 				universe;

	@SuppressWarnings("unchecked")
	public Box2DSystem() {
		super( Aspect.getAspectForAll(Transform.class, PhysicsBodyComponent.class) );
	}

	@Override
	public void initialize() {
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
		debugRenderer 	= new Box2DDebugRenderer();
		universe		= new Hashtable<Integer,World>();
	}
	
	@Override
	protected void inserted(Entity e) {
		World b2World;
		if( universe.containsKey(transformMapper.get(e).mapId) ) {
			b2World = universe.get(transformMapper.get(e).mapId);
		} else {
			b2World = new World(new Vector2(0, 0), true);
			universe.put( transformMapper.get(e).mapId, b2World );
		}
		bodyMapper.get(e).physicsBody.initialize( b2World );
		
		bodyMapper.get(e).getBody().setTransform(
				transformMapper.get(e).x,
				transformMapper.get(e).y, 
				transformMapper.get(e).rotation
			);
	}
	
	@Override
	protected void removed(Entity e) {
		universe.get( transformMapper.get(e).mapId ).destroyBody( bodyMapper.get(e).getBody() );
		if( universe.get( transformMapper.get(e).mapId ).getBodyCount() == 0 ) {
			universe.remove( transformMapper.get(e).mapId );
		}
	}
	
	@Override
	protected void process(Entity e) {
		if( velocityMapper.getSafe(e) != null ) {
			bodyMapper.get(e).getBody().setLinearVelocity(velocityMapper.get(e).speed.cpy().mul(60));			
		}
	}
	
	protected void end() {
		ImmutableBag<Entity> cameras = world.getManager(GroupManager.class).getEntities("CAMERA");
		Entity e = cameras.get(0);
		OrthographicCamera camera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		camera.translate( 
				transformMapper.get(e).x - cameraMapper.get(e).position.x, 
				transformMapper.get(e).y - cameraMapper.get(e).position.y
			);
		camera.rotate( cameraMapper.get(e).rotation - transformMapper.get(e).rotation );
		camera.zoom = cameraMapper.get(e).zoom;
		camera.update();

		for( Integer i : universe.keySet() ) {
			World b2World = universe.get(i);
			b2World.step(1/60f, 6, 2);
			debugRenderer.render(b2World, camera.combined);
		}
		
		
		
		for (int i = 0, s = getActives().size(); s > i; i++) {
			e = getActives().get(i);
			transformMapper.get(e).setLocation(bodyMapper.get(e).getBody().getPosition());
		}
	}
}	