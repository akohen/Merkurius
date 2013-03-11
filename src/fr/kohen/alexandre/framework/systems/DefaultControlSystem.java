package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.base.C.STATES;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Velocity;

public class DefaultControlSystem extends EntityProcessingSystem {

	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected float 						speedUp, speedDown, speedLeft, speedRight;
	protected boolean						simpleScheme = false;

	@SuppressWarnings("unchecked")
	public DefaultControlSystem(float speedUp, float speedDown, float speedLeft, float speedRight) {
		super( Aspect.getAspectForAll(Player.class, Velocity.class, EntityState.class) );
		this.speedUp = speedUp;
		this.speedDown = speedDown;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
	}
	
	public DefaultControlSystem(float speed) {
		this(speed, speed, speed, speed);
	}
	
	public DefaultControlSystem(float speedVertical, float speedHorizontal) {
		this(speedVertical, speedVertical, speedHorizontal, speedHorizontal);
	}
	
	@SuppressWarnings("unchecked")
	public DefaultControlSystem(Class<? extends Component> componentType, float speedUp, float speedDown, float speedLeft, float speedRight) {
		super( Aspect.getAspectForAll(componentType) );
		this.speedUp = speedUp;
		this.speedDown = speedDown;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
	}

	@Override
	public void initialize() {
		velocityMapper = ComponentMapper.getFor(Velocity.class, world);
		stateMapper = ComponentMapper.getFor(EntityState.class, world);
	}

	@Override
	protected void process(Entity e) {
		
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Vector2 	accel 		= new Vector2(0,0);
		
		// Adding speed according to input if the player can move
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			
			if ( Gdx.input.isKeyPressed(Keys.LEFT) ) {
				accel.x = -speedLeft;
			}
			
			if ( Gdx.input.isKeyPressed(Keys.RIGHT) ) {
				accel.x = speedRight;
			} 
			
			if ( Gdx.input.isKeyPressed(Keys.UP) ) {
				accel.y = speedUp;
			}
			
			if ( Gdx.input.isKeyPressed(Keys.DOWN) ) {
				accel.y = -speedDown;
			}
			
			if ( simpleScheme ) {
				velocity.setSpeed(accel);
			} else {
				velocity.addSpeed(accel);
			}
			
		
			if( velocity.getSpeed().len() == 0 ) {
				state.setState(STATES.IDLE);
			} else { 
				state.setState(STATES.MOVING);
			}
			
		}	
	}

}
