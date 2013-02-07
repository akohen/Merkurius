package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.C;
import fr.kohen.alexandre.framework.C.STATES;
import fr.kohen.alexandre.framework.Visual;
import fr.kohen.alexandre.framework.VisualAnimation;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.components.VisualComponent;

/**
 * Updates the animations
 * @author Alexandre
 */
public class AnimationSystem extends EntityProcessingSystem {
	protected ComponentMapper<EntityState> 		stateMapper;
	protected ComponentMapper<VisualComponent> 	visualMapper;
	protected ComponentMapper<Velocity> 		velocityMapper;

	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super( Aspect.getAspectForAll(VisualComponent.class, EntityState.class) );
	}

	@Override
	public void initialize() {
		stateMapper 	= ComponentMapper.getFor(EntityState.class, world);
		visualMapper 	= ComponentMapper.getFor(VisualComponent.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
	}

	@Override
	protected void process(Entity e) {
		EntityState 	state 	= stateMapper.get(e);
		Vector2 		speed = velocityMapper.get(e).getSpeed();
		Visual 			visual	= visualMapper.get(e).getVisual();
		
		visual.update( world.getDelta() );
		if( visual.isAnimated() ) {
			setCurrentAnim(visual, state, speed);
		}
		
	}
	
	
	protected void setCurrentAnim(Visual visual, EntityState state, Vector2 speed) {
		VisualAnimation visualAnim = (VisualAnimation) visual;
		
		if( state.getState() == STATES.MOVING ) {
			visualAnim.setCurrentAnim( getAnim(visualAnim, speed) );
		} else if( state.getState() == STATES.IDLE ) {
			visualAnim.setCurrentAnim( idleAnim(visualAnim) );
		}
	}
	
	
	protected String getAnim(VisualAnimation spatial, Vector2 speed) {
		String currentAnim = C.WALK_LEFT;
		if( speed.len() == 0 )
			return idleAnim(spatial);
			
		if( speed.x < 0 ) {
			if( speed.y < 0 )
				currentAnim = C.WALK_DOWN_LEFT;
			else if( speed.y > 0 )
				currentAnim = C.WALK_UP_LEFT;
			else 
				currentAnim = C.WALK_LEFT;
		} else if( speed.x > 0 ) {
			if( speed.y < 0 )
				currentAnim = C.WALK_DOWN_RIGHT;
			else if( speed.y > 0 )
				currentAnim = C.WALK_UP_RIGHT;
			else 
				currentAnim = C.WALK_RIGHT;
		} else {
			if( speed.y > 0 )
				currentAnim = C.WALK_UP;
			else 
				currentAnim = C.WALK_DOWN;
		}
		return currentAnim;
	}
	
	
	/**
	 * Sets the current animation to the idle animation
	 */
	protected String idleAnim(VisualAnimation visual) {
		String currentAnim = visual.getCurrentAnim();
		
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