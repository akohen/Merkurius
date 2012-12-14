package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

public class ControlSystemBase extends EntityProcessingSystem implements KeyListener {

	protected GameContainer container;
	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<Transform> 	transformMapper;	
	protected boolean moveRight;
	protected boolean moveLeft;
	protected boolean moveUp;
	protected boolean moveDown;
	protected MapSystem mapSystem;
	protected float speedUp, speedDown, speedLeft, speedRight;

	@SuppressWarnings("unchecked")
	public ControlSystemBase(GameContainer container, float speedUp, float speedDown, float speedLeft, float speedRight) {
		super(Player.class);
		this.container = container;
		this.speedUp = speedUp;
		this.speedDown = speedDown;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
	}
	
	public ControlSystemBase(GameContainer container, float speed) {
		this(container, speed, speed, speed, speed);
	}
	
	public ControlSystemBase(GameContainer container, float speedVertical, float speedHorizontal) {
		this(container, speedVertical, speedVertical, speedHorizontal, speedHorizontal);
	}
	
	@SuppressWarnings("unchecked")
	public ControlSystemBase(GameContainer container, Class<? extends Component> componentType, float speedUp, float speedDown, float speedLeft, float speedRight) {
		super(componentType);
		this.container = container;
		this.speedUp = speedUp;
		this.speedDown = speedDown;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
	}

	@Override
	public void initialize() {
		this.velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		this.stateMapper 		= new ComponentMapper<EntityState>(EntityState.class, world);
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.mapSystem 			= Systems.get(MapSystem.class, 	world);
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		Vector2f 	accel 		= new Vector2f(0,0);
		
		// Set the map where the player is as the active map
		if( transform.getMapId() != mapSystem.getCurrentMap() )
			mapSystem.enterMap( transform.getMapId() );
		
		// Adding speed according to input if the player can move
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			if (moveLeft) 
				accel.x = -speedLeft;
			if (moveRight)
				accel.x = speedRight;
			if (moveUp)
				accel.y = -speedUp;
			if (moveDown)
				accel.y = speedDown;
			velocity.addSpeed(accel);
		
			if( velocity.getSpeed().length() == 0 )
				state.setState(STATES.IDLE);
			else 
				state.setState(STATES.MOVING);
		}	
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_LEFT) {
			moveLeft = true;
			moveRight = false;
		} else if (key == Input.KEY_RIGHT) {
			moveRight = true;
			moveLeft = false;
		} else if (key == Input.KEY_UP) {
			moveUp = true;
			moveDown = false;
		} else if (key == Input.KEY_DOWN) {
			moveDown = true;
			moveUp = false;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_LEFT) {
			moveLeft = false;
		} else if (key == Input.KEY_RIGHT) {
			moveRight = false;
		} else if (key == Input.KEY_UP) {
			moveUp = false;
		} else if (key == Input.KEY_DOWN) {
			moveDown = false;
		} 
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}
	
	@Override
	public void setInput(Input input) {}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

}
