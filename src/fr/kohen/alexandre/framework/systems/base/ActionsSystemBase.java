package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Actions;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.ActionList;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.CollisionSystem;

public class ActionsSystemBase extends EntityProcessingSystem implements MouseListener {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<Actions> 		actionsMapper;
	protected GameContainer 				container;
	protected CollisionSystem				collisionSystem;
	protected CameraSystem 					cameraSystem;
	protected Vector2f	 					mousePosition = new Vector2f( 0,0 );
	protected Transform						mouseTransform;
	protected Entity						mouse;
	protected boolean						clicked = false;
	protected boolean						dragged = false;

	@SuppressWarnings("unchecked")
	public ActionsSystemBase(GameContainer container) {
		super( Aspect.getAspectForAll(Actions.class) );
		this.container = container;
	}

	@Override
	public void initialize() {
		cameraSystem 		= Systems.get(CameraSystem.class,world);
		collisionSystem		= Systems.get(CollisionSystem.class,world);
		container.getInput().addMouseListener(this);
	}
	
	
	protected void end() { clicked = false; dragged = false; }
	
	
	@Override
	protected void process(Entity e) {
		
		if( mouse == null )
			mouse = world.getManager(TagManager.class).getEntity("mouse");
		
		ActionList actions = actionsMapper.get(e).getActionList();
		
		if( mouse != null && collisionSystem != null ) {
			if( collisionSystem.checkCollision(e, mouse) || collisionSystem.isContained(e, mouse) ) { // If the mouse touches the entity
				if( clicked ) { // If the mouse is clicked, click the entity
					actions.onMouseClick(e);
				}
					
				else // If the mouse is not clicked, hover the entity
					actions.onMouseHover(e);
			}
			else {
				if( clicked )
					actions.onMouseOffClick(e); 
				else
					actions.onMouseOff(e);
			}
		} // If mouseSystem && collisionSystem	
	}
	
	@Override
	public void setInput(Input input) { }

	@Override
	public boolean isAcceptingInput() { return true; }

	@Override
	public void inputEnded() { }

	@Override
	public void inputStarted() { }

	@Override
	public void mouseWheelMoved(int change) { }

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) { }

	@Override
	public void mousePressed(int button, int x, int y) { 
		mousePosition.x = x;
		mousePosition.y = y;
		clicked = true;
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