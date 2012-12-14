package fr.kohen.alexandre.games.space.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;

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

public class ControlSystem extends EntityProcessingSystem implements KeyListener {

	protected GameContainer container;
	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<Transform> 	transformMapper;	
	protected boolean moveRight;
	protected boolean moveLeft;
	protected boolean moveUp;
	protected boolean moveDown;
	protected MapSystem mapSystem;

	@SuppressWarnings("unchecked")
	public ControlSystem(GameContainer container) {
		super(Player.class);
		this.container = container;
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
		float 		accel 		= 0f;
		float		rotation	= 0f;
		
		// Set the map where the player is as the active map
		if( transform.getMapId() != mapSystem.getCurrentMap() )
			mapSystem.enterMap( transform.getMapId() );
		
		// Adding speed according to input if the player can move
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			
			velocity.addRotation(-1 * Math.signum(velocity.getRotation()) );
			if (moveLeft) 
				rotation -= 2;
			if (moveRight)
				rotation += 2;
			if (moveUp)
				accel += 2f;
			if (moveDown)
				accel -= 2f;
			
			
			Vector2f speed = new Vector2f( Math.max(0, accel + velocity.getVelocity()) ,0);
			speed.setTheta( transform.getRotation() - 90 );

			velocity.addRotation(rotation);
			velocity.setSpeed(speed);
			
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
