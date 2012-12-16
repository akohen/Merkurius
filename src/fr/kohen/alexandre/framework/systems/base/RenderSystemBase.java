package fr.kohen.alexandre.framework.systems.base;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Spatial;
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
	protected MapSystem 					mapSystem;
	protected CameraSystem 					cameraSystem;

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
		mapSystem			= Systems.get(MapSystem.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
	}

	@Override
	protected void added(Entity e) {
	}
	
	
	@Override
	protected void begin() {
		if( cameraSystem != null ) { // Getting camera list from the camera system
			cameras = cameraSystem.getCameras();
		}
	}
	
	
	
	@Override
	protected void process(Entity e) {
		
		for(Entity camera : cameras) {			
			if( isVisible(e, camera) ) // Adding visible entity to the camera rendering list
				cameraMapper.get(camera).addEntity(e);
		} // Foreach camera
		
		if( cameras == null || cameras.isEmpty() )
			defaultRender(e); // Default camera system if no camera is defined
	}
	
	
	@Override
	protected void end() {
		
		for(Entity camera : cameras) {
			setCamera(camera);	// Setting graphics context according to camera
			
			// Drawing objects
			for(Entity e : cameraMapper.get(camera).getEntities() ) {
				spatialFormMapper.get(e).getSpatial().render(graphics, transformMapper.get(e));	
			}
			
			// Resetting graphics context for the next camera
			resetCamera();
		}
	}


	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	
	@Override
	public boolean isVisible(Entity e, Entity camera) {
		if( transformMapper.get(camera).getMapId() == transformMapper.get(e).getMapId() ) {
			
			Shape cameraShape = new Rectangle(0,0,cameraMapper.get(camera).getScreenWidth(),cameraMapper.get(camera).getScreenHeight());
			cameraShape.setLocation( transformMapper.get(camera).getLocation().sub(cameraMapper.get(camera).getOffset() ) );
			cameraShape = cameraShape.transform( org.newdawn.slick.geom.Transform.createRotateTransform(
					(float) -Math.toRadians(transformMapper.get(camera).getRotation()),
					transformMapper.get(camera).getLocation().x,
					transformMapper.get(camera).getLocation().y)
				);
			
			Spatial spatial = spatialFormMapper.get(e).getSpatial();
			Shape entityShape = spatial.getShape();
			entityShape.setLocation( transformMapper.get(e).getLocation().sub(spatial.getOffset()) );
			entityShape = entityShape.transform( org.newdawn.slick.geom.Transform.createRotateTransform(
					(float) Math.toRadians(transformMapper.get(e).getRotation()),
					transformMapper.get(e).getLocation().x,
					transformMapper.get(e).getLocation().y)
				);
			
			if( cameraShape.intersects(entityShape) || cameraShape.contains(entityShape) )
				return true;
			else return false;
		}
		else return false;
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
