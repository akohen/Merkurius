package fr.kohen.alexandre.framework.systems.sideView;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

public class GravitySystem extends EntityProcessingSystem {

	private ComponentMapper<Velocity> 	velocityMapper;
	private ComponentMapper<Transform> 	positionMapper;
	private MapSystem 					mapSystem;

	@SuppressWarnings("unchecked")
	public GravitySystem() {
		super(Velocity.class);
	}

	@Override
	public void initialize() {
		velocityMapper 	= new ComponentMapper<Velocity>	(Velocity.class, world);
		positionMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		mapSystem 		= Systems.get					(MapSystem.class, world);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);
		Transform 	position 	= positionMapper.get(e);
		float 		gravity 	= Float.parseFloat(mapSystem.getMap(position.getMapId()).getMap().getMapProperty("gravity", "0"));
		
		velocity.addSpeed(0, gravity);
	}
}
