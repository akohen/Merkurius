package fr.kohen.alexandre.framework.systems;

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
	private ComponentMapper<Transform> 				transformMapper;
	private ComponentMapper<Velocity> 				velocityMapper;
	private ComponentMapper<CameraComponent> 		cameraMapper;
	private ComponentMapper<PhysicsBodyComponent> 	bodyMapper;
	private Box2DDebugRenderer 						debugRenderer;
	private World 									box2dworld;

	@SuppressWarnings("unchecked")
	public Box2DSystem() {
		super( Aspect.getAspectForAll(Transform.class, Velocity.class, PhysicsBodyComponent.class) );
	}

	@Override
	public void initialize() {
		box2dworld 		= new World(new Vector2(0, 0), true); 
		debugRenderer 	= new Box2DDebugRenderer();
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
	}
	
	@Override
	protected void inserted(Entity e) {
		bodyMapper.get(e).physicsBody.initialize( box2dworld );
		
		bodyMapper.get(e).getBody().setTransform(
				transformMapper.get(e).x,
				transformMapper.get(e).y, 
				transformMapper.get(e).rotation
			);
	}
	
	@Override
	protected void removed(Entity e) {
		box2dworld.destroyBody( bodyMapper.get(e).getBody() );
	}
	
	@Override
	protected void process(Entity e) {
		bodyMapper.get(e).getBody().setLinearVelocity(velocityMapper.get(e).speed.cpy().mul(60));
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

		box2dworld.step(1/60f, 6, 2);
		debugRenderer.render(box2dworld, camera.combined);
		
		for (int i = 0, s = getActives().size(); s > i; i++) {
			e = getActives().get(i);
			transformMapper.get(e).setLocation(bodyMapper.get(e).getBody().getPosition());
		}
	}
}	