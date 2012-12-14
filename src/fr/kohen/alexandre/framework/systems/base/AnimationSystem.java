package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.Spatial;

public class AnimationSystem extends EntityProcessingSystem {
	protected ComponentMapper<EntityState> stateMapper;
	protected ComponentMapper<SpatialForm> spatialMapper;
	protected ComponentMapper<Velocity> velocityMapper;

	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super(SpatialForm.class, EntityState.class);
	}

	@Override
	public void initialize() {
		stateMapper 	= new ComponentMapper<EntityState>(EntityState.class, world);
		spatialMapper 	= new ComponentMapper<SpatialForm>(SpatialForm.class, world);
		velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
	}

	@Override
	protected void process(Entity e) {
		EntityState 	state 	= stateMapper.get(e);
		Vector2f 		speed 	= velocityMapper.get(e).getSpeed();
		Spatial 		spatial	= spatialMapper.get(e).getSpatial();
		
		if( spatial.hasAnim() ) {
			if( state.getState() == STATES.MOVING ) {
				spatial.setCurrentAnim(getAnim(spatial, speed));
			}
			else if( state.getState() == STATES.IDLE ) {
				spatial.setCurrentAnim(idleAnim(spatial));
			}
		}
		
	}
	
	
	
	protected String getAnim(Spatial spatial, Vector2f speed) {
		String currentAnim = C.WALK_LEFT;
		if( speed.length() == 0 )
			return idleAnim(spatial);
			
		if( speed.x < 0 ) {
			if( speed.y > 0 )
				currentAnim = C.WALK_DOWN_LEFT;
			else if( speed.y < 0 )
				currentAnim = C.WALK_UP_LEFT;
			else 
				currentAnim = C.WALK_LEFT;
		}
		else if( speed.x > 0 ) {
			if( speed.y > 0 )
				currentAnim = C.WALK_DOWN_RIGHT;
			else if( speed.y < 0 )
				currentAnim = C.WALK_UP_RIGHT;
			else 
				currentAnim = C.WALK_RIGHT;
		}
		else {
			if( speed.y < 0 )
				currentAnim = C.WALK_UP;
			else 
				currentAnim = C.WALK_DOWN;
		}
		return currentAnim;
	}
	
	
	/**
	 * Sets the current animation to the idle animation
	 */
	protected String idleAnim(Spatial spatial) {
		String currentAnim = spatial.getCurrentAnim();
		
		if (currentAnim.equalsIgnoreCase(C.WALK_LEFT)) {
			currentAnim = C.STAND_LEFT;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_RIGHT)) {
			currentAnim = C.STAND_RIGHT;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_UP)) {
			currentAnim = C.STAND_UP;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_DOWN)) {
			currentAnim = C.STAND_DOWN;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_DOWN_LEFT)) {
			currentAnim = C.STAND_DOWN_LEFT;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_DOWN_RIGHT)) {
			currentAnim = C.STAND_DOWN_RIGHT;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_UP_LEFT)) {
			currentAnim = C.STAND_UP_LEFT;
		} else if (currentAnim.equalsIgnoreCase(C.WALK_UP_RIGHT)) {
			currentAnim = C.STAND_UP_RIGHT;
		}
		return currentAnim;
	}
}	