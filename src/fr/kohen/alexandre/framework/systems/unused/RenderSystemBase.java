package fr.kohen.alexandre.framework.systems.unused;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class RenderSystemBase extends EntityProcessingSystem implements RenderSystem {
	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected ComponentMapper<SpatialForm> 	spatialFormMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected Camera						camera;
	protected Vector2f 						screen;
	protected float							rotation = 0;
	protected ArrayList<Spatial> 			spatialArray;
	protected ArrayList<Spatial> 			OffScreen;
	protected ArrayList<Spatial> 			differential;
	protected ArrayList<Spatial> 			background;
	protected ArrayList<Spatial> 			foreground;
	protected ArrayList<Spatial> 			effects;
	protected ArrayList<Spatial> 			guiArray;
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
		screen 				= new Vector2f(container.getWidth(), container.getHeight());
		mapSystem			= Systems.get(MapSystem.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
		camera				= new Camera();
	}

	@Override
	protected void added(Entity e) {
	}
	
	
	
	@Override
	protected void begin() {
		// Getting map shift from the camera system
		//if( cameraSystem != null )
		//	camera = cameraSystem.getCamera();
		
		// Initializating layers
		spatialArray 	= new ArrayList<Spatial>();
		OffScreen 		= new ArrayList<Spatial>();
		differential	= new ArrayList<Spatial>();
		background 		= new ArrayList<Spatial>();
		foreground 		= new ArrayList<Spatial>();
		effects 		= new ArrayList<Spatial>();
		guiArray 		= new ArrayList<Spatial>();
	}
	
	
	
	@Override
	protected void process(Entity e) {
		Spatial 	spatial 	= spatialFormMapper	.get(e).getSpatial();
		Transform 	transform 	= transformMapper	.get(e);
		
		if( transform.getMapId() == -1 || (mapSystem != null && transform.getMapId() == mapSystem.getCurrentMap()) ) {
			spatial.setTransform(transform);
			if( "GUI" == world.getGroupManager().getGroupOf(e) )
				guiArray.add(spatial);
			else if( "background" == world.getGroupManager().getGroupOf(e) )
				background.add(spatial);
			else if( "foreground" == world.getGroupManager().getGroupOf(e) )
				foreground.add(spatial);
			else if( "differential" == world.getGroupManager().getGroupOf(e) )
				differential.add(spatial);
			else if( "effects" == world.getGroupManager().getGroupOf(e) )
				effects.add(spatial);
			else if (transform.getX() >= -200-camera.getPosition().x 
					&& transform.getY() >= -200-camera.getPosition().y 
					&& transform.getX() < container.getWidth()-camera.getPosition().x 
					&& transform.getY() < container.getHeight()-camera.getPosition().y && spatial != null) {
				spatialArray.add(spatial);	
			}
			else {
				OffScreen.add(spatial);
			}
		}		
	}
	
	
	@Override
	protected void end() {

		// reactivate sorting ?
		// Collections.sort(spatialArray);
		
		// various shifts
		Camera diffCamera = new Camera(camera.getScreenSize().x, camera.getScreenSize().y);
		diffCamera.setRotation( camera.getRotation() );
		diffCamera.setPosition( new Vector2f(camera.getPosition().x/2.0f, camera.getPosition().y) );
		
		// Render map background
		for (Spatial spatial : background)
			spatial.render(graphics);
				

		if( mapSystem != null && mapSystem.getCurrentMap() > -1 )
			mapSystem.renderLayers("back", camera);
			
				
		for (Spatial spatial : differential) {
			spatial.render(graphics, diffCamera);
			spatial.update(world.getDelta());
		}
			
		
		// Render entities in order (from back to front)		
		for (Spatial spatial : spatialArray) {
			spatial.render(graphics, camera);
			spatial.update(world.getDelta());
		}
		
		// Render map foreground
		if( mapSystem != null && mapSystem.getCurrentMap() > -1 )
			mapSystem.renderLayers("front", camera);
		
		// Render foreground
		for (Spatial spatial : foreground) {
			spatial.render(graphics, camera);
			spatial.update(world.getDelta());
		}
			
		// Render effects
		for (Spatial spatial : effects) {
			spatial.render(graphics);
			spatial.update(world.getDelta());
		}
		
		// Render UI
		for (Spatial spatial : guiArray) {
			spatial.render(graphics);
			spatial.update(world.getDelta());
		}
	}


	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	public boolean isVisible(Entity e, Entity camera) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCamera(Entity camera) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defaultRender(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetCamera() {
		// TODO Auto-generated method stub
		
	}
	
}
