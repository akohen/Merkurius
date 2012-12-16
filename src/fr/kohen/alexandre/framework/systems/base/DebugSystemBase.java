package fr.kohen.alexandre.framework.systems.base;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.components.HitboxForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;


public class DebugSystemBase extends EntitySystem implements KeyListener {
	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected ComponentMapper<HitboxForm> 	hitboxFormMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected ComponentMapper<Camera> 		cameraMapper;
	protected List<Entity>					cameras;
	protected RenderSystem 					renderSystem;
	protected CameraSystem 					cameraSystem;
	protected boolean						showDebug = false;


	public DebugSystemBase(GameContainer container) {
		//super(Unused.class);
		this.container = container;
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize() {
		hitboxFormMapper	= new ComponentMapper<HitboxForm>	(HitboxForm.class, world);
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		cameraMapper		= new ComponentMapper<Camera>		(Camera.class, world);
		container.getInput().addKeyListener(this);
		renderSystem		= Systems.get(RenderSystem.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
	}
	
	@Override
	protected void begin() {
		if( cameraSystem != null )
			for(Entity c : cameraSystem.getCameras() ) {
				renderSystem.setCamera(c);	// Setting graphics context according to camera
				
				// Drawing objects
				for(Entity e : cameraMapper.get(c).getEntities() ) {
					Vector2f location = transformMapper.get(e).getLocation();
					
					graphics.setColor(Color.green); 
					graphics.drawLine(location.x-5, location.y, location.x+5, location.y);
					graphics.drawLine(location.x, location.y-5, location.x, location.y+5);
					
					if( hitboxFormMapper.get(e) != null)
						hitboxFormMapper.get(e).getSpatial().render(graphics, transformMapper.get(e), Color.red);	
				}
				
				renderSystem.resetCamera();			
			} // foreach camera
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

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) { }
	
}
