package fr.kohen.alexandre.framework.systems;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;
import fr.kohen.alexandre.framework.systems.interfaces.VisualDrawSystem;

public class DefaultVisualSystem extends EntityProcessingSystem implements VisualDrawSystem {
	protected ComponentMapper<VisualComponent> 	visualMapper;
	protected ComponentMapper<Transform> 		transformMapper;
	private Map<Entity, Visual>					visuals 			= new HashMap<Entity, Visual>();
	private Map<String, Visual> 				availableVisuals 	= new HashMap<String, Visual>();
	
	
	@SuppressWarnings("unchecked")
	public DefaultVisualSystem() {
		super( Aspect.getAspectForAll(VisualComponent.class) );
	}
	
	public DefaultVisualSystem(Map<String,Visual> newVisuals) {
		this();
		availableVisuals.putAll(newVisuals);
	}
	
	@Override
	public void initialize() {
		visualMapper 	= ComponentMapper.getFor(VisualComponent.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		
		availableVisuals.put("mouse_box", new BoxVisual(10, 10, Color.GRAY));
	}

	@Override
	protected void process(Entity e) {
	}
	
	@Override
	protected void inserted(Entity e) {
		if ( availableVisuals.containsKey(visualMapper.get(e).type) ) {
			visuals.put(e, availableVisuals.get(visualMapper.get(e).type));
		} else {
			throw new RuntimeException("No visual returned for " + visualMapper.get(e).type);
		}
	}
	
	
	public void draw(Entity e, SpriteBatch batch) {
		visuals.get(e).draw( visualMapper.get(e), transformMapper.get(e), batch );
	}

	@Override
	public boolean canProcess(Entity e) {
		return visualMapper.has(e);
	}
	
}