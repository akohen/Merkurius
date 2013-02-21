package fr.kohen.alexandre.framework.systems.base;

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

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;


public class RenderSystemBase extends EntityProcessingSystem implements RenderSystem {
	@Mapper ComponentMapper<SpatialForm> 	spatialFormMapper;
	@Mapper ComponentMapper<Camera> 		cameraMapper;
	@Mapper ComponentMapper<Transform> 		transformMapper;
	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected ImmutableBag<Entity>			cameras;
	protected Vector2f 						screen;
	protected float							rotation = 0;
	protected CameraSystem 					cameraSystem;
	protected MapSystem 					mapSystem;

	@SuppressWarnings("unchecked")
	public RenderSystemBase(GameContainer container) {
		super( Aspect.getAspectForAll(Transform.class, SpatialForm.class) );
		this.container = container;
		this.graphics = container.getGraphics();
	}

	@Override
	public void initialize() {
		screen 				= new Vector2f(container.getWidth(), container.getHeight());
		cameraSystem		= Systems.get(CameraSystem.class, world);
		mapSystem			= Systems.get(MapSystem.class, world);
	}
	
	@Override
	protected void begin() {
		cameras = world.getManager(GroupManager.class).getEntities("CAMERA");
		for (int i = 0, s = cameras.size(); s > i; i++) {
			cameraMapper.get(cameras.get(i)).clearEntities();
		}
	}
	
	
	
	@Override
	protected void process(Entity e) {
		if( cameras == null || cameras.isEmpty() ) {
			defaultRender(e); // Default camera system if no camera is defined
		}
		else {
			for (int i = 0, s = cameras.size(); s > i; i++) {		
				if( cameraSystem.isVisible(e, cameras.get(i)) ) // Adding visible entity to the camera rendering list
					cameraMapper.get(cameras.get(i)).addEntity(e);
			} // Foreach camera
		}
	}
	
	
	@Override
	protected void end() {
		
		for (int i = 0, s = cameras.size(); s > i; i++) {
			Entity camera = cameras.get(i);
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
