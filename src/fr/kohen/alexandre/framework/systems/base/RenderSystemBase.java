package fr.kohen.alexandre.framework.systems.base;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class RenderSystemBase extends EntityProcessingSystem implements RenderSystem {
	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected ComponentMapper<SpatialForm> 	spatialFormMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected ComponentMapper<Camera> 		cameraMapper;
	protected List<Entity>					cameras;
	protected Vector2f 						screen;
	protected float							rotation = 0;
	protected CameraSystem 					cameraSystem;
	protected MapSystem 					mapSystem;

	@SuppressWarnings("unchecked")
	public RenderSystemBase(GameContainer container) {
		super(Transform.class, SpatialForm.class);
		this.container = container;
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize() {
		spatialFormMapper	= new ComponentMapper<SpatialForm>	(SpatialForm.class, world);
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		cameraMapper		= new ComponentMapper<Camera>		(Camera.class, world);
		screen 				= new Vector2f(container.getWidth(), container.getHeight());
		cameraSystem		= Systems.get(CameraSystem.class, world);
		mapSystem			= Systems.get(MapSystem.class, world);
		cameras				= new ArrayList<Entity>();
	}

	@Override
	protected void added(Entity e) {
	}
	
	
	@Override
	protected void begin() {
		if( cameraSystem != null ) { // Getting camera list from the camera system
			cameras = cameraSystem.getCameras();
			for(Entity camera : cameras)
				cameraMapper.get(camera).clearEntities();
		}
	}
	
	
	
	@Override
	protected void process(Entity e) {
		if( cameras == null || cameras.isEmpty() ) {
			defaultRender(e); // Default camera system if no camera is defined
		}
		else {
			for(Entity camera : cameras) {			
				if( cameraSystem.isVisible(e, camera) ) // Adding visible entity to the camera rendering list
					cameraMapper.get(camera).addEntity(e);
			} // Foreach camera
		}
	}
	
	
	@Override
	protected void end() {
		
		for(Entity camera : cameras) {
			setCamera(camera);	// Setting graphics context according to camera
			
			
			if( mapSystem != null && mapSystem.getCurrentMap() > -1 )
				mapSystem.renderLayers("back", camera);
			
			// Drawing objects
			for(Entity e : cameraMapper.get(camera).getEntities()) {
				if( e.isActive() )
					spatialFormMapper.get(e).getSpatial().render(graphics, transformMapper.get(e));	
			}
			
			//if( mapSystem != null && mapSystem.getCurrentMap() > -1 )
			//	mapSystem.renderLayers("front", camera);
			
			
			// Resetting graphics context for the next camera
			resetCamera();
		}
	}


	@Override
	protected boolean checkProcessing() {
		return true;
	}
	

	
	@Override
	public void setCamera(Entity camera) {
		Vector2f 	cameraLocation 	= transformMapper.get(camera).getLocation();
		Camera		cameraComponent	= cameraMapper.get(camera);
		
		// Setting clip according to the camera definition so anything outside the camera viewport isn't drawn
		graphics.rotate(
				cameraLocation.x + cameraComponent.getScreenX(), 
				cameraLocation.y + cameraComponent.getScreenY(), 
				cameraComponent.getScreenRotation()
			);
		
		graphics.setWorldClip(
				cameraComponent.getScreenX() - cameraComponent.getScreenWidth()/2, 
				cameraComponent.getScreenY() - cameraComponent.getScreenHeight()/2, 
				cameraComponent.getScreenWidth(), 
				cameraComponent.getScreenHeight() 
			);

		graphics.rotate(
				cameraLocation.x + cameraComponent.getScreenX(), 
				cameraLocation.y + cameraComponent.getScreenY(), 
				transformMapper.get(camera).getRotation()
			);
		
		graphics.translate(
				- cameraLocation.x + cameraComponent.getScreenX(), 
				- cameraLocation.y + cameraComponent.getScreenY()
			);
		
		
	}

	
	@Override
	public void defaultRender(Entity e) {
		graphics.setClip(0, 0, container.getWidth(), container.getHeight());
		spatialFormMapper.get(e).getSpatial().render(graphics, transformMapper.get(e));	
		graphics.clearClip();
		graphics.resetTransform();
	}

	
	@Override
	public void resetCamera() {
		graphics.clearWorldClip();
		graphics.resetTransform();	
	}
	
}
