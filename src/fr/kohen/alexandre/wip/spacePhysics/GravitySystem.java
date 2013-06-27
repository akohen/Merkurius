package fr.kohen.alexandre.wip.spacePhysics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;

public class GravitySystem extends EntityProcessingSystem {
	protected ComponentMapper<PhysicsBodyComponent> 	bodyMapper;
	public final float G = 0.0000000000667384f;
	
	@SuppressWarnings("unchecked")
	public GravitySystem() {
		super( Aspect.getAspectForAll(PhysicsBodyComponent.class, GravityComponent.class) );
	}
	
	@Override
	protected void initialize() {
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
	}
	
	protected void begin() {
		applyGravityToAll();
	}

	private void applyGravityToAll() {
		for (int i = 0, s = getActives().size(); s > i; i++) {
			Body body1 = bodyMapper.get( getActives().get(i) ).getBody();
			for (int j = i+1; s > j; j++) {
				Body body2 = bodyMapper.get( getActives().get(j) ).getBody();
				if( body1.getWorld() == body2.getWorld() ) {
					applyGravityBetweenBodies(body1, body2);
				}
			}
		}
	}

	private void applyGravityBetweenBodies(Body body1, Body body2) {
		Gdx.app.log( "Physics", "Applying gravity between: " + body1.getMass() + " and " + body2.getMass() );
		Vector2 delta = new Vector2( body2.getWorldCenter() ).sub( body1.getWorldCenter() );
		Gdx.app.log( "Physics", "Delta: " + delta + " length: " + delta.len() );
		float force = body1.getMass() * body2.getMass() / ( delta.len() * delta.len() );
		delta.nor();
		body1.applyForceToCenter(delta.cpy().mul(force));
		body2.applyForceToCenter(delta.cpy().mul(-force));
		
	}

	@Override
	protected void process(Entity e) {	
	}

}
