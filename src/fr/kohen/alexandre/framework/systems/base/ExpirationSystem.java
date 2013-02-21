package fr.kohen.alexandre.framework.systems.base;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Expires;

public class ExpirationSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Expires> expiresMapper;

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
		expires.reduceLifeTime((int) world.getDelta());

		if (expires.isExpired()) {
			world.deleteEntity(e);
		}

	}
}
