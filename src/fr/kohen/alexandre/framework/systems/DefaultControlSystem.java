package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.base.C.STATES;
import fr.kohen.alexandre.framework.base.KeyBindings;
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
		KeyBindings.addKey(Keys.LEFT, "move_left");
		KeyBindings.addKey(Keys.RIGHT, "move_right");
		KeyBindings.addKey(Keys.UP, "move_up");
		KeyBindings.addKey(Keys.DOWN, "move_down");
		
		KeyBindings.addKey(Keys.Q, "move_left");
		KeyBindings.addKey(Keys.D, "move_right");
		KeyBindings.addKey(Keys.Z, "move_up");
		KeyBindings.addKey(Keys.S, "move_down");
	}

	@Override
	protected void process(Entity e) {
		
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Vector2 	accel 		= new Vector2(0,0);
		
		// Adding speed according to input if the player can move
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			
			if ( KeyBindings.isKeyPressed("move_left") ) {
				accel.x = -speedLeft;
			}
			
			if ( KeyBindings.isKeyPressed("move_right") ) {
				accel.x = speedRight;
			} 
			
			if ( KeyBindings.isKeyPressed("move_up") ) {
				accel.y = speedUp;
			}
			
			if ( KeyBindings.isKeyPressed("move_down") ) {
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
