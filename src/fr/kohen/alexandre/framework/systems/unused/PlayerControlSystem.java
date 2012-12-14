package fr.kohen.alexandre.framework.systems.unused;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.npc.DialogSystem;
import fr.kohen.alexandre.framework.systems.npc.InteractionSystemBase;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

/**
 * Player controls
 * @author Alexandre
 *
 */
public class PlayerControlSystem extends EntityProcessingSystem implements KeyListener {
	private GameContainer container;
	private boolean moveRight;
	private boolean moveLeft;
	private boolean moveUp;
	private boolean moveDown;
	private boolean interaction;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<EntityState> 	stateMapper;
	private ComponentMapper<SpatialForm> 	spatialMapper;
	private ComponentMapper<Transform> 		transformMapper;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem(GameContainer container) {
		super(Velocity.class, Player.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
		stateMapper 	= new ComponentMapper<EntityState>(EntityState.class, world);
		spatialMapper 	= new ComponentMapper<SpatialForm>(SpatialForm.class, world);
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		String 		playerAnim 	= spatialMapper		.get(e).getSpatial().getCurrentAnim();
		Vector2f 	accel 		= new Vector2f(0,0);
		
		if( transform.getMapId() != world.getSystemManager().getSystem(MapSystemBase.class).getCurrentMap() )
			world.getSystemManager().getSystem(MapSystemBase.class).enterMap( transform.getMapId() );
		
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			if (interaction) { // Add an interaction marker
				Vector2f shift = e.getComponent(Transform.class).getLocation();
				shift.x += 12; shift.y += 12;
				if( playerAnim.equalsIgnoreCase(C.WALK_LEFT) || playerAnim.equalsIgnoreCase(C.STAND_LEFT) )
					shift.x -= 18;
				if( playerAnim.equalsIgnoreCase(C.WALK_RIGHT) || playerAnim.equalsIgnoreCase(C.STAND_RIGHT) )
					shift.x += 18;
				if( playerAnim.equalsIgnoreCase(C.WALK_UP) || playerAnim.equalsIgnoreCase(C.STAND_UP) )
					shift.y -= 20;
				if( playerAnim.equalsIgnoreCase(C.WALK_DOWN) || playerAnim.equalsIgnoreCase(C.STAND_DOWN) )
					shift.y += 20;
				world.getSystemManager().getSystem(InteractionSystemBase.class).addMarker(e, shift);
				interaction = false;				
			}
			if (moveLeft) {
				accel.x = -1;
			}
			if (moveRight) {
				accel.x = 1;
			}
			if (moveUp) {
				accel.y = -1;
			}
			if (moveDown) {
				accel.y = 1;
			}		
			velocity.addSpeed(accel);
		
			if( velocity.getSpeed().length() == 0 ) {
				state.setState(STATES.IDLE);
			}
			else state.setState(STATES.MOVING);
			
				
		}
		else if ( state.getState() == STATES.TALKING ) {
			if (interaction) { // Cancel speech
				world.getSystemManager().getSystem(DialogSystem.class).stopDialog(e);
				interaction = false;
			}
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
			moveUp = false;
			moveDown = true;
		} else if (key == Input.KEY_SPACE) {
			interaction = true;
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
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) {
	}
	

}
