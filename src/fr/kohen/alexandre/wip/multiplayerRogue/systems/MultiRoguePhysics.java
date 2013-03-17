package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.wip.multiplayerRogue.components.Destination;

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
