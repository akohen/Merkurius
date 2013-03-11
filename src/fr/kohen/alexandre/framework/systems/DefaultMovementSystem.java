package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

/**
 * Updates the entities position according to the velocity.
 * This system must me called after all other systems affecting the entities position (such as control or collision systems for example)
 * 
 * @author Alexandre
 */
public class DefaultMovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<Velocity> 		velocityMapper;

	@SuppressWarnings("unchecked")
	public DefaultMovementSystem() {
		super( Aspect.getAspectForAll(Transform.class, Velocity.class) );
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);
		Transform 	transform 	= transformMapper.get(e);			
		transform.position.x += velocity.getSpeed().x;
		transform.position.y += velocity.getSpeed().y;
		transform.rotation += velocity.getRotation();
		transform.rotation = (transform.rotation + 360)%360;
	}
}	