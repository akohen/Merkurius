package fr.kohen.alexandre.examples.multiplayerRogue.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.examples.multiplayerRogue.components.Destination;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;

public class MultiRoguePhysics extends DefaultBox2DSystem {
	
	private ComponentMapper<Destination> destMapper;

	protected void initialize() {
		super.initialize();
		this.destMapper = ComponentMapper.getFor(Destination.class, world);
	}
	
	protected void updateBody(Entity e) {
		super.updateBody(e);
		if( destMapper.has(e) ) {
			Vector2 movement = transformMapper.get(e).getPosition2().sub(destMapper.get(e).position);
			bodyMapper.get(e).getBody().setLinearVelocity(movement.mul(-10f));
		}
	}
}
