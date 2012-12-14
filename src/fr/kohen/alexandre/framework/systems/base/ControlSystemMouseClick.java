package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Destination;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.engine.C.STATES;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

public class ControlSystemMouseClick extends EntityProcessingSystem implements MouseListener {

	protected GameContainer 				container;
	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected ComponentMapper<Destination> 	destinationMapper;
	protected MapSystem 					mapSystem;
	protected CameraSystem 					cameraSystem;
	protected Vector2f	 					mousePosition = new Vector2f( 0,0 );

	@SuppressWarnings("unchecked")
	public ControlSystemMouseClick(GameContainer container) {
		super(Player.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		this.velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		this.stateMapper 		= new ComponentMapper<EntityState>(EntityState.class, world);
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.destinationMapper 	= new ComponentMapper<Destination>(Destination.class, world);
		this.mapSystem 			= Systems.get(MapSystem.class, 	world);
		this.cameraSystem 		= Systems.get(CameraSystem.class,world);
		container.getInput().addMouseListener(this);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.get(e);
		EntityState	state		= stateMapper		.get(e);
		Transform	transform	= transformMapper	.get(e);
		Destination	dest		= destinationMapper	.get(e);
		
		// Set the map where the player is as the active map
		if( mapSystem != null && transform.getMapId() != mapSystem.getCurrentMap() )
			mapSystem.enterMap( transform.getMapId() );
		
		// Adding speed according to input if the player can move
		if( state.getState() == STATES.IDLE || state.getState() == STATES.MOVING ) {
			dest.setLocation(mousePosition);
			/*
			accel.add( transform.getLocation().negate() );

			float rotation = (float) (accel.getTheta() - transform.getRotation());
			if( rotation > 180 )
				rotation -= 360;
			
			if( Math.abs(rotation) > 3 )
				velocity.setRotation( 3f * Math.signum(rotation) );
			 */
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
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {  
	}

}
