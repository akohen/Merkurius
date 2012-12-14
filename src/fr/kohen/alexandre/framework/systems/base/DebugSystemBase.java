package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.HitboxForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class DebugSystemBase extends EntityProcessingSystem implements KeyListener {
	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected ComponentMapper<HitboxForm> 	hitboxFormMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected Camera						camera;
	protected MapSystem 					mapSystem;
	protected CameraSystem 					cameraSystem;
	protected boolean						showDebug = false;

	@SuppressWarnings("unchecked")
	public DebugSystemBase(GameContainer container) {
		super(Transform.class, HitboxForm.class);
		this.container = container;
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize() {
		hitboxFormMapper	= new ComponentMapper<HitboxForm>	(HitboxForm.class, world);
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		container.getInput().addKeyListener(this);
		mapSystem			= Systems.get(MapSystem.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
		camera = new Camera();
	}
	
	@Override
	protected void begin() {
		// Getting map shift from the camera system
		if( cameraSystem != null )
			camera = cameraSystem.getCamera();
	}
	

	
	@Override
	protected void process(Entity e) {
		HitboxForm 	hitbox 		= hitboxFormMapper	.get(e);
		Transform 	transform 	= transformMapper	.get(e);
		
		Vector2f pos = transform.getLocation().add( camera.getPosition() );
		
		if( transform.getMapId() == -1 || (mapSystem != null && transform.getMapId() == mapSystem.getCurrentMap()) ) {
			graphics.setColor(Color.red);
			graphics.rotate( camera.getScreenSize().x/2, camera.getScreenSize().y/2, camera.getRotation() );
			graphics.draw( hitbox.getShape(pos, transform.getRotationAsRadians()) );
			
			graphics.setColor(Color.green); 
			graphics.drawLine(pos.x-5, pos.y, pos.x+5, pos.y);
			graphics.drawLine(pos.x, pos.y-5, pos.x, pos.y+5);
			graphics.resetTransform();
		}		
	}


	@Override
	protected boolean checkProcessing() { return showDebug; }

	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		
	}

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
