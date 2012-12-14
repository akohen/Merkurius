package fr.kohen.alexandre.framework.systems.base;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class MovementSystemFloat extends EntityProcessingSystem {
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Transform> transformMapper;

	@SuppressWarnings("unchecked")
	public MovementSystemFloat() {
		super(Transform.class, Velocity.class);
	}

	@Override
	public void initialize() {
		velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);
		Transform 	transform 	= transformMapper.get(e);			
		transform.addVector(velocity.getSpeed());
		transform.addRotation(velocity.getRotation());
	}
}	