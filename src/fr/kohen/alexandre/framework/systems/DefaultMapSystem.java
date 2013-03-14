package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.components.MapComponent;
import fr.kohen.alexandre.framework.systems.interfaces.MapDrawSystem;

public class DefaultMapSystem extends EntityProcessingSystem implements MapDrawSystem {
	protected ComponentMapper<MapComponent> 	mapMapper;
	
	
	@SuppressWarnings("unchecked")
	public DefaultMapSystem() {
		super( Aspect.getAspectForAll(MapComponent.class) );
	}
	
	@Override
	public void initialize() {
		mapMapper 	= ComponentMapper.getFor(MapComponent.class, world);
	}

	@Override
	protected void process(Entity e) {
	}
	
	@Override
	protected void inserted(Entity e) {
		
	}
	
	
	public void draw(Entity e, SpriteBatch batch) {
		
	}

	@Override
	public boolean canProcess(Entity e) {
		return mapMapper.has(e);
	}
	
}