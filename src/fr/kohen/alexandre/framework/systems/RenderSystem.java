package fr.kohen.alexandre.framework.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

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
	protected SpriteBatch 						batch;
	protected ICameraSystem 					cameraSystem;
	protected IMapSystem 						mapSystem;
	protected ImmutableBag<Entity> 				cameras;
	protected FrameBuffer 						framebuffer;
	
	
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

		batch 			= new SpriteBatch();
		cameras			= new Bag<Entity>();
		framebuffer 	= new FrameBuffer(Format.RGBA4444, 640, 480, true);
		
	}

	
	@Override
	protected void begin() {
		if ( cameraSystem != null ) {
			cameras = cameraSystem.getCameras();
		}
		//cameras = world.getManager(GroupManager.class).getEntities("CAMERA");
		/*for (int i = 0, s = cameras.size(); s > i; i++) {
			cameraMapper.get(cameras.get(i)).entities.clear();
		}*/
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	
	
	@Override
	protected void process(Entity e) {
		/* if( cameras == null || cameras.isEmpty() ) {
			//defaultRender(e); // Default camera system if no camera is defined
		} else {
			for (int i = 0, s = cameras.size(); s > i; i++) {		
				if( cameraSystem.isVisible(e, cameras.get(i)) ) {// Adding visible entity to the camera rendering list
					cameraMapper.get(cameras.get(i)).entities.add(e);
				}
			} // Foreach camera
		} */
	}

	
	@Override
	protected void end() {
		OrthographicCamera mainCamera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		mainCamera.setToOrtho(true);
		
		for ( int i = 0, s = cameras.size(); s > i; i++ ) {
			framebuffer.begin();
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			Entity cameraEntity = cameras.get(i);
			
			OrthographicCamera camera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
			camera.rotate( - transformMapper.get(cameraEntity).rotation );
			camera.translate(
					transformMapper.get(cameraEntity).x, 
					transformMapper.get(cameraEntity).y
				);
			camera.update();
			
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			
			if ( mapSystem != null && mapSystem.getCurrentMap() > -1 )
				mapSystem.renderLayers("back", cameraEntity);
			
			// Drawing objects
			for ( Entity e : cameraMapper.get(cameraEntity).entities ) {
				if ( e.isActive() && e.isEnabled() ) {
					if ( visualMapper.getSafe(e) != null ) {
						visualMapper.get(e).draw( transformMapper.get(e), batch );
					}
				}
			}
			
			if( mapSystem != null && mapSystem.getCurrentMap() > -1 )
				mapSystem.renderLayers("front", cameraEntity);
			
			
			batch.end();
			framebuffer.end();
			
			TextureRegion region = new TextureRegion(
					framebuffer.getColorBufferTexture(), 
					(int) ((Gdx.graphics.getWidth() - cameraMapper.get(cameraEntity).size.x) / 2), 
					(int) ((Gdx.graphics.getHeight() - cameraMapper.get(cameraEntity).size.y) / 2), 
					(int) cameraMapper.get(cameraEntity).size.x, 
					(int) cameraMapper.get(cameraEntity).size.y
				);
			
			Sprite sprite = new Sprite(region);
			batch.setProjectionMatrix(mainCamera.combined);
			batch.begin();
			
			sprite.setPosition(
					(Gdx.graphics.getWidth() - cameraMapper.get(cameraEntity).size.x)/2 + cameraMapper.get(cameraEntity).position.x, 
					(Gdx.graphics.getHeight() - cameraMapper.get(cameraEntity).size.y)/2 - cameraMapper.get(cameraEntity).position.y
				);
			sprite.rotate(cameraMapper.get(cameraEntity).rotation);
			sprite.draw(batch);
			batch.end();
		}
		
		
		
	}

	

	@Override
	public void defaultRender(Entity e) {
		visualMapper.get(e).draw(transformMapper.get(e), batch);
	}

	
	
}
