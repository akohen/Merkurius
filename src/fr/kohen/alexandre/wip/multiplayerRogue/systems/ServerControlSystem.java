package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.wip.multiplayerRogue.components.*;

public class ServerControlSystem extends EntityProcessingSystem {
	private ComponentMapper<Destination> destMapper;
	private ComponentMapper<Input> inputMapper;
	private ComponentMapper<Transform> transformMapper;

	@SuppressWarnings("unchecked")
	public ServerControlSystem() {
		super( Aspect.getAspectForAll( Input.class, Destination.class ) );
	}
	
	public void initialize() {
		this.inputMapper = ComponentMapper.getFor(Input.class, world);
		this.destMapper = ComponentMapper.getFor(Destination.class, world);
		this.transformMapper = ComponentMapper.getFor(Transform.class, world);
	}

	@Override
	protected void process(Entity player) {

		destMapper.get(player).lastUpdate += world.getDelta();
		Vector2 movement = transformMapper.get(player).getPosition2().sub(destMapper.get(player).position);
		
		if( movement.x > 22 ) {
			destMapper.get(player).position.x += 20;
		} else if( movement.x < -22 ) {
			destMapper.get(player).position.x -= 20;
		} else if( movement.y > 22 ) {
			destMapper.get(player).position.y += 20;
		} else if( movement.y < -22 ) {
			destMapper.get(player).position.y -= 20;
		}
		
		if( canMove(player) ) {
			switch( inputMapper.get(player).input1 ) {
			case 1:
				destMapper.get(player).position.y += 20;
				break;
			case 2:
				destMapper.get(player).position.x += 20;
				break;
			case 3:
				destMapper.get(player).position.y -= 20;
				break;
			case 4:
				destMapper.get(player).position.x -= 20;
				break;
			}
			destMapper.get(player).lastUpdate = 0;
		}
	}
	
	private boolean canMove(Entity e) {
		return inputMapper.get(e).input1 > 0 && destMapper.get(e).lastUpdate > 1;
	}

}
