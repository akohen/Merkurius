package fr.kohen.alexandre.framework.systems.base;

import java.awt.geom.AffineTransform;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;

public class MouseSystemBase extends EntityProcessingSystem implements MouseListener {

	protected GameContainer 				container;
	protected ComponentMapper<Transform> 	transformMapper;
	protected ComponentMapper<Camera> 		cameraMapper;
	protected ComponentMapper<SpatialForm> 	spatialMapper;
	protected Vector2f	 					mousePosition = new Vector2f( 0,0 );
	protected CameraSystem 					cameraSystem;
	protected Spatial 						mouseHitbox;

	@SuppressWarnings("unchecked")
	public MouseSystemBase(GameContainer container) {
		super(Camera.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		cameraMapper 		= new ComponentMapper<Camera>(Camera.class, world);
		spatialMapper 		= new ComponentMapper<SpatialForm>(SpatialForm.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
		container.getInput().addMouseListener(this);
		mouseHitbox			= new BoxSpatial(1, 1);
		mouseHitbox.initalize();
	}
	
	@Override
	protected void begin() {
	}

	@Override
	protected void process(Entity camera) {
		
		Entity mouse;
		double[] pt = {
				mousePosition.x - cameraMapper.get(camera).getScreenX() + transformMapper.get(camera).getX(), 
				mousePosition.y - cameraMapper.get(camera).getScreenY() + transformMapper.get(camera).getY()
			};
		AffineTransform.getRotateInstance(
				Math.toRadians(-cameraMapper.get(camera).getScreenRotation()-transformMapper.get(camera).getRotation()), 
				transformMapper.get(camera).getX(), 
				transformMapper.get(camera).getY()
			).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		float newX = (float) pt[0];
		float newY = (float) pt[1];
		Transform mouseTransform = new Transform(transformMapper.get(camera).getMapId(), newX, newY);

		if( cameraSystem.isVisible(mouseTransform, mouseHitbox, camera) ) {
			if( cameraMapper.get(camera).getMouse() == null ) {
				mouse = EntityFactory.createMouse(
						world, 
						mouseTransform,
						camera 
					);
				cameraMapper.get(camera).setMouse(mouse);
			}
			else {
				mouse = cameraMapper.get(camera).getMouse();
				transformMapper.get(mouse).setMapId( transformMapper.get(camera).getMapId() );
				transformMapper.get(mouse).setX(newX);
				transformMapper.get(mouse).setY(newY);
			}
		}
		else if( cameraMapper.get(camera).getMouse() != null ) {
			cameraMapper.get(camera).getMouse().delete();
			cameraMapper.get(camera).setMouse(null);
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
