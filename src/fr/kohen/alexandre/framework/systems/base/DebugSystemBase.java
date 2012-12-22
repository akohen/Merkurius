package fr.kohen.alexandre.framework.systems.base;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.components.HitboxForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.spatials.CircleSpatial;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class DebugSystemBase extends EntityProcessingSystem implements KeyListener {
	@Mapper ComponentMapper<HitboxForm> 	hitboxFormMapper;
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<Camera> 		cameraMapper;
	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected List<Entity>					cameras;
	protected RenderSystem 					renderSystem;
	protected CameraSystem 					cameraSystem;
	protected boolean						showDebug = false;
	protected Color[]						cameraColors = {Color.blue, Color.yellow, Color.magenta, Color.white, Color.pink, Color.cyan, Color.gray, Color.orange};
	protected int 							colorCount;
	protected Spatial 						mouseSpatial;


	@SuppressWarnings("unchecked")
	public DebugSystemBase(GameContainer container) {
		super( Aspect.getAspectForAll(Camera.class) );
		this.container = container;
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize() {
		container.getInput().addKeyListener(this);
		renderSystem		= Systems.get(RenderSystem.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
		mouseSpatial		= new CircleSpatial(10);
		mouseSpatial.initalize();		
	}
	
	
	@Override
	protected void begin() {
		colorCount = 0;
	}
	
	
	@Override
	protected void process(Entity camera) {
		renderSystem.setCamera(camera);	// Setting graphics context according to camera
		
		// Drawing objects
		for(Entity e : cameraMapper.get(camera).getEntities() ) {
			if( e.isActive() ) {
				Vector2f location = transformMapper.get(e).getLocation();
				
				graphics.setColor(Color.green); 
				graphics.drawLine(location.x-5, location.y, location.x+5, location.y);
				graphics.drawLine(location.x, location.y-5, location.x, location.y+5);
				
				if( hitboxFormMapper.get(e) != null)
					hitboxFormMapper.get(e).getSpatial().render(graphics, transformMapper.get(e), Color.red);	
			}			
		}
		
		// Mouse pointer
		if( cameraMapper.get(camera).getMouse() != null )
			mouseSpatial.render(graphics, transformMapper.get(cameraMapper.get(camera).getMouse()), cameraColors[colorCount%8]);
		
		// Camera box
		Vector2f 	cameraLocation 	= transformMapper.get(camera).getLocation();
		Rectangle cameraShape = new Rectangle(0,0,cameraMapper.get(camera).getScreenWidth()-1,cameraMapper.get(camera).getScreenHeight()-1 );
		cameraShape.setLocation( 
				cameraLocation.x-cameraMapper.get(camera).getScreenWidth()/2,
				cameraLocation.y-cameraMapper.get(camera).getScreenHeight()/2
			);
		graphics.rotate( 0, 0, -transformMapper.get(camera).getRotation() );
		graphics.setColor(cameraColors[colorCount%8]); 
		graphics.draw(cameraShape);
		
		renderSystem.resetCamera();	
		colorCount++;
	}
	
	
	@Override
	protected boolean checkProcessing() { return showDebug; }

	@Override
	public void setInput(Input input) { }

	@Override
	public boolean isAcceptingInput() { return true; }

	@Override
	public void inputEnded() { }

	@Override
	public void inputStarted() { }

	@Override
	public void keyPressed(int key, char c) {
		if ( key == Input.KEY_R ) {
			showDebug = !showDebug;
			container.setShowFPS(showDebug);
		}
	}

	@Override
	public void keyReleased(int key, char c) { }
	
}
