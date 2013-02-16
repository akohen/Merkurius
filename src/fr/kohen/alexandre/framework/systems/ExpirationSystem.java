package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Expires;

/**
 * Expires entity after some time
 * 
 * @author Alexandre
 */
public class ExpirationSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Expires> 		expiresMapper;

	@SuppressWarnings("unchecked")
	public ExpirationSystem() {
		super( Aspect.getAspectForAll(Expires.class) );
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void process(Entity e) {
		Expires expires = expiresMapper.get(e);
		expires.reduceLifeTime((int) (world.getDelta()*1000) );

		if (expires.isExpired()) {
			world.deleteEntity(e);
		}
	}
}	