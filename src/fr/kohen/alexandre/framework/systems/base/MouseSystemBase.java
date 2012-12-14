package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Mouse;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

public class MouseSystemBase extends EntityProcessingSystem implements MouseListener {

	protected GameContainer 				container;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected CameraSystem 					cameraSystem;
	protected MapSystem 					mapSystem;
	protected Vector2f	 					mousePosition = new Vector2f( 0,0 );
	protected Transform						mouseTransform;

	@SuppressWarnings("unchecked")
	public MouseSystemBase(GameContainer container) {
		super(Mouse.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		this.stateMapper 		= new ComponentMapper<EntityState>(EntityState.class, world);
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.cameraSystem 		= Systems.get(CameraSystem.class,world);
		this.mapSystem 			= Systems.get(MapSystem.class,world);
		container.getInput().addMouseListener(this);
	}

	@Override
	protected void process(Entity e) {
		if( mouseTransform == null )
			mouseTransform = transformMapper.get(e);
		
		if( mouseTransform != null ) {
			if( cameraSystem != null ) {
				mousePosition.add( cameraSystem.getCamera().getPosition().negate() );
			}
			if( mapSystem != null ) {
				mouseTransform.setMapId(mapSystem.getCurrentMap());
			}
			mouseTransform.setLocation(mousePosition);
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
	}

	@Override
	public void mouseReleased(int button, int x, int y) { 
		mousePosition.x = x;
		mousePosition.y = y;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) { 
		mousePosition.x = newx;
		mousePosition.y = newy;
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) { 
		mousePosition.x = newx;
		mousePosition.y = newy;
	}

	



}
