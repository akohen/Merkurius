package fr.kohen.alexandre.framework.systems.base;

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
public class MovementSystemFloat extends EntityProcessingSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<Velocity> 		velocityMapper;

	@SuppressWarnings("unchecked")
	public MovementSystemFloat() {
		super( Aspect.getAspectForAll(Transform.class, Velocity.class) );
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);
		Transform 	transform 	= transformMapper.get(e);			
		transform.addVector(velocity.getSpeed());
		transform.addRotation(velocity.getRotation());
	}
}	