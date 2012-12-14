package fr.kohen.alexandre.tests.systems;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;


public class RenderSystemTemp extends EntityProcessingSystem implements RenderSystem {

	protected Graphics 						graphics;
	protected GameContainer 				container;
	protected ComponentMapper<SpatialForm> 	spatialFormMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected Camera						camera;
	protected ArrayList<Spatial> 			spatialArray;
	protected CameraSystem 					cameraSystem;
	
	
	@SuppressWarnings("unchecked")
	public RenderSystemTemp(GameContainer container) {
		super(Transform.class, SpatialForm.class);
		this.container = container;
		this.graphics = container.getGraphics();
	}
	
	
	
	@Override
	public void initialize() {
		spatialFormMapper	= new ComponentMapper<SpatialForm>	(SpatialForm.class, world);
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		//screen 				= new Vector2f(container.getWidth(), container.getHeight());
		//mapSystem			= Systems.get(MapSystem.class, world);
		cameraSystem		= Systems.get(CameraSystem.class, world);
		camera				= new Camera();
	}
	
	
	@Override
	protected void begin() {
		// Getting map shift from the camera system
		if( cameraSystem != null )
			camera = cameraSystem.getCamera();
		
		// Initializating layers
		//TODO Change layer behavior
		spatialArray 	= new ArrayList<Spatial>();
	}
	
	
	@Override
	protected void process(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
