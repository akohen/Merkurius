package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.Systems;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.systems.interfaces.ICameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.IMapSystem;
import fr.kohen.alexandre.framework.systems.interfaces.IRenderSystem;

public class RenderSystem extends EntityProcessingSystem implements IRenderSystem {
	protected ComponentMapper<VisualComponent> 	visualMapper;
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<CameraComponent> 	cameraMapper;
	protected OrthographicCamera 				camera;
	protected SpriteBatch 						batch;
	protected Sprite 							sprite;
	protected ICameraSystem 					cameraSystem;
	protected IMapSystem 						mapSystem;
	protected ImmutableBag<Entity> 				cameras;
	
	
	@SuppressWarnings("unchecked")
	public RenderSystem() {
		super(Aspect.getAspectForAll(Transform.class, VisualComponent.class));
	}
	
	@Override
	public void initialize() {
		visualMapper 	= ComponentMapper.getFor(VisualComponent.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		cameraSystem	= Systems.get(ICameraSystem.class, world);
		mapSystem		= Systems.get(IMapSystem.class, world);

		camera 			= new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		batch 			= new SpriteBatch();
		cameras			= new Bag<Entity>();
	}

	
	@Override
	protected void begin() {
		cameras = world.getManager(GroupManager.class).getEntities("CAMERA");
		for (int i = 0, s = cameras.size(); s > i; i++) {
			cameraMapper.get(cameras.get(i)).entities.clear();
		}
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	
	
	@Override
	protected void process(Entity e) {
		if( cameras == null || cameras.isEmpty() ) {
			//defaultRender(e); // Default camera system if no camera is defined
		} else {
			for (int i = 0, s = cameras.size(); s > i; i++) {		
				if( cameraSystem.isVisible(e, cameras.get(i)) ) {// Adding visible entity to the camera rendering list
					cameraMapper.get(cameras.get(i)).entities.add(e);
				}
			} // Foreach camera
		}
	}

	
	@Override
	protected void end() {
		for ( int i = 0, s = cameras.size(); s > i; i++ ) {
			Entity camera = cameras.get(i);
			setCamera(camera);	// Setting graphics context according to camera
			
			if ( mapSystem != null && mapSystem.getCurrentMap() > -1 )
				mapSystem.renderLayers("back", camera);
			
			// Drawing objects
			for ( Entity e : cameraMapper.get(camera).entities ) {
				if ( e.isActive() )
					visualMapper.get(e).draw(transformMapper.get(e), batch);
			}
			
			if( mapSystem != null && mapSystem.getCurrentMap() > -1 )
				mapSystem.renderLayers("front", camera);
			
			// Resetting graphics context for the next camera
			resetCamera();
		}
	}

	@Override
	public void setCamera(Entity e) {
		camera = new OrthographicCamera( cameraMapper.get(e).size.x, cameraMapper.get(e).size.y );
		camera.translate( cameraMapper.get(e).position );
		camera.rotate( cameraMapper.get(e).rotation );
		camera.zoom = cameraMapper.get(e).zoom;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	public void defaultRender(Entity e) {
		visualMapper.get(e).draw(transformMapper.get(e), batch);
	}

	@Override
	public void resetCamera() {	
		batch.end();
	}
	
}
