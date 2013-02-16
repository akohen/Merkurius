package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Transform;

/**
 * Updates the entities position according to the velocity.
 * This system must me called after all other systems affecting the entities position (such as control or collision systems for example)
 * 
 * @author Alexandre
 */
public class DebugSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;

	@SuppressWarnings("unchecked")
	public DebugSystem() {
		super( Aspect.getAspectForAll(Transform.class) );
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void process(Entity e) {
		//Transform 	transform 	= transformMapper.get(e);
	}
}	