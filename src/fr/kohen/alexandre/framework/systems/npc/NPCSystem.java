package fr.kohen.alexandre.framework.systems.npc;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Map;
import fr.kohen.alexandre.framework.components.Pathfinding;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;

public class NPCSystem extends EntityProcessingSystem {
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Pathfinding> NPCMapper;
	private MapSystemBase mapSystem;
	private ComponentMapper<EntityState> stateMapper;

	@SuppressWarnings("unchecked")
	public NPCSystem() {
		super(Transform.class, Velocity.class, Pathfinding.class);
	}

	@Override
	public void initialize() {
		this.mapSystem 			= world.getSystemManager().getSystem(MapSystemBase.class);
		this.velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.NPCMapper 			= new ComponentMapper<Pathfinding>(Pathfinding.class, world);
		this.stateMapper 		= new ComponentMapper<EntityState>(EntityState.class, world);
	}

	@Override
	protected void process(Entity e) {
		Pathfinding npc 	= NPCMapper		.get(e);
		EntityState	state	= stateMapper	.get(e);
		if( npc.getPath() != null && (state.getState() == STATES.IDLE || state.getState() == STATES.MOVING) ) { // if a path has been set			
			Map 		map 	 = mapSystem		.getMap( transformMapper.get(e).getMapId() );
			Vector2f 	location = transformMapper	.get(e).getLocation();
			Velocity 	velocity = velocityMapper	.get(e);
			
			// Go to the next step if we're close enough
			if( npc.getStep(map.getTileDimension()).distance( location ) < 1 )
				npc.nextStep();
			
			if( npc.getPath() != null ) { // If the goal has not been reached
				Vector2f destination = new Vector2f( 
						npc.getStep(map.getTileDimension()).x, 
						npc.getStep(map.getTileDimension()).y);					
						
				Vector2f speed = new Vector2f();
				if( destination.x > location.x )
					speed.x = 1;
				else if( destination.x < location.x )
					speed.x =-1;
				if( destination.y > location.y )
					speed.y = 1;
				else if( destination.y < location.y )
					speed.y =-1;
				velocity.addSpeed(speed);
				state.setState(STATES.MOVING);
			}
			else { // Goal reached, removing destination
				npc.removeDestination();
				state.setState(STATES.IDLE);
			}
		}
	}
	
}	