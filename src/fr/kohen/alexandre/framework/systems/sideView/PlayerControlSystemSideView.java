package fr.kohen.alexandre.framework.systems.sideView;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Jump;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;
import fr.kohen.alexandre.framework.systems.interfaces.InteractionSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


/**
 * Player controls
 * @author Alexandre
 *
 */
public class PlayerControlSystemSideView extends EntityProcessingSystem implements KeyListener {
	protected GameContainer container;
	protected boolean moveRight;
	protected boolean moveLeft;
	protected boolean moveUp;
	protected boolean interaction;
	protected boolean acceptInteraction = false;
	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<SpatialForm> 	spatialMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected ComponentMapper<Jump> 		jumpMapper;
	protected MapSystem						mapSystem;
	protected InteractionSystem				interactionSystem;
	protected Sound 						jumpSound;

	@SuppressWarnings("unchecked")
	public PlayerControlSystemSideView(GameContainer container) {
		super(Velocity.class, Player.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		velocityMapper 	= new ComponentMapper<Velocity>		(Velocity.class, 	world);
		transformMapper = new ComponentMapper<Transform>	(Transform.class, 	world);
		stateMapper 	= new ComponentMapper<EntityState>	(EntityState.class, world);
		spatialMapper 	= new ComponentMapper<SpatialForm>	(SpatialForm.class, world);
		jumpMapper 		= new ComponentMapper<Jump>			(Jump.class, 		world);
		mapSystem 		= Systems.get						(MapSystem.class, 	world);
		interactionSystem = Systems.get						(InteractionSystem.class, world);
		jumpSound 		= ResourceManager.getSound("jump");
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		Jump		jump		= jumpMapper		.get(e);
		String 		playerAnim 	= spatialMapper		.get(e).getSpatial().getCurrentAnim();
		Vector2f 	accel 		= new Vector2f(0,0);
		
		// Set the active map to the map where the player is
		if( transform.getMapId() != mapSystem.getCurrentMap() )
			mapSystem.enterMap( transform.getMapId() );
		
		acceptInteraction = false;
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			acceptInteraction = true;
			if (interaction) { // Add an interaction marker
				Vector2f shift = transform.getLocation();
				shift.x += -60; shift.y += 55;
				if( playerAnim.contains( new String("left")) )
					shift.x -= 0;
				if( playerAnim.contains( new String("right")) )
					shift.x += 140;
				interactionSystem.addMarker(e, shift);
				interaction = false;				
			}
			if (moveLeft) {
				accel.x = -1.5f;
			}
			if (moveRight) {
				accel.x = 1.5f;
			}
			if (moveUp && jump.canJump() ) {
				accel.y = -13;
				jumpSound.play();
			}	
			velocity.addSpeed(accel);
		
			if( velocity.getSpeed().length() == 0 ) {
				state.setState(STATES.IDLE);
			}
			else state.setState(STATES.MOVING);
			
				
		}
		else if ( state.getState() == STATES.TALKING ) {
			/*if (interaction) { // Cancel speech
				world.getSystemManager().getSystem(DialogSystem.class).stopDialog(e);
				interaction = false;
			}*/
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
		} else if (key == Input.KEY_SPACE && acceptInteraction) {
			interaction = true;
		} else if (key == Input.KEY_ESCAPE ) {
			ResourceManager.getMusic("music").stop();
			container.exit();
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
		} else if (key == Input.KEY_SPACE) {
			//moveUp = false;
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
