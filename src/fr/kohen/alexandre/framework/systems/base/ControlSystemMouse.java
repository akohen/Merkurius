package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
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
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

public class ControlSystemMouse extends EntityProcessingSystem implements MouseListener {

	protected GameContainer 				container;
	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected MapSystem 					mapSystem;
	protected CameraSystem 					cameraSystem;
	protected float 						speedUp, speedDown, speedLeft, speedRight;
	protected Vector2f	 					mousePosition = new Vector2f( 0,0 );

	@SuppressWarnings("unchecked")
	public ControlSystemMouse(GameContainer container, float speedUp, float speedDown, float speedLeft, float speedRight) {
		super(Player.class);
		this.container = container;
		this.speedUp = speedUp;
		this.speedDown = speedDown;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
	}
	
	public ControlSystemMouse(GameContainer container, float speed) {
		this(container, speed, speed, speed, speed);
	}
	
	public ControlSystemMouse(GameContainer container, float speedVertical, float speedHorizontal) {
		this(container, speedVertical, speedVertical, speedHorizontal, speedHorizontal);
	}
	
	@SuppressWarnings("unchecked")
	public ControlSystemMouse(GameContainer container, Class<? extends Component> componentType, float speedUp, float speedDown, float speedLeft, float speedRight) {
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
		this.cameraSystem 		= Systems.get(CameraSystem.class,world);
		container.getInput().addMouseListener(this);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		Vector2f 	accel 		= mousePosition.copy();
		
		// Set the map where the player is as the active map
		if( mapSystem != null && transform.getMapId() != mapSystem.getCurrentMap() )
			mapSystem.enterMap( transform.getMapId() );
		
		// Adding speed according to input if the player can move
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			// Updating acceleration			
			accel.add( transform.getLocation().negate() );
			
			accel.x = Math.min(speedRight, accel.x);
			accel.x = Math.max(-speedLeft, accel.x);
			accel.y = Math.min(speedDown, accel.y);
			accel.y = Math.max(-speedUp, accel.y);
			
			velocity.setSpeed(accel);
		
			if( velocity.getSpeed().length() == 0 )
				state.setState(STATES.IDLE);
			else 
				state.setState(STATES.MOVING);
		}	
	}
	


	@Override
	public boolean isAcceptingInput() { return true; }
	
	@Override
	public void setInput(Input input) {}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public void mouseWheelMoved(int change) { }

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) { }

	@Override
	public void mousePressed(int button, int x, int y) {
		mousePosition.x = x;
		mousePosition.y = y;
		if( cameraSystem != null )
			mousePosition.add( cameraSystem.getCamera().getPosition().negate() );
	}

	@Override
	public void mouseReleased(int button, int x, int y) { }

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) { 
		mousePosition.x = newx;
		mousePosition.y = newy;
		if( cameraSystem != null )
			mousePosition.add( cameraSystem.getCamera().getPosition().negate() );
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) { 
		mousePosition.x = newx;
		mousePosition.y = newy;
		if( cameraSystem != null )
			mousePosition.add( cameraSystem.getCamera().getPosition().negate() );
	}

}
