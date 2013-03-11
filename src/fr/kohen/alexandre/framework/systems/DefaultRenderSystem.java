package fr.kohen.alexandre.framework.systems;

import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;
import fr.kohen.alexandre.framework.systems.interfaces.VisualDrawSystem;

public class DefaultRenderSystem extends EntityProcessingSystem implements RenderSystem {
	protected ComponentMapper<VisualComponent> 	visualMapper;
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<CameraComponent> 	cameraMapper;
	protected SpriteBatch 						batch;
	protected FrameBuffer 						framebuffer;
	protected OrthographicCamera 				mainCamera;
	protected VisualDrawSystem visualSystem;
	
	
	@SuppressWarnings("unchecked")
	public DefaultRenderSystem() {
		super(Aspect.getAspectForAll(CameraComponent.class));
		
		batch 			= new SpriteBatch();
		framebuffer 	= new FrameBuffer(Format.RGBA4444, 640, 480, true);
	}
	
	@Override
	public void initialize() {
		visualMapper 	= ComponentMapper.getFor(VisualComponent.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		
		visualSystem	= Systems.get(VisualDrawSystem.class, world);
		if( visualSystem == null ) throw new RuntimeException("A required system is not loaded");
	}

	
	@Override
	protected void begin() {
		clearScreen();
		mainCamera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		mainCamera.setToOrtho(true);
	}
	
	
	@Override
	protected void process(Entity cameraEntity) {
		framebuffer.begin();
			clearScreen();
			batch.setProjectionMatrix( setCamera(cameraEntity).combined );
			batch.begin();
				drawObjects( cameraMapper.get(cameraEntity).entities );
			batch.end();
		framebuffer.end();
		drawToScreen(cameraEntity);
	}
	
	
	private void clearScreen() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	

	private OrthographicCamera setCamera(Entity cameraEntity) {
		OrthographicCamera camera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		camera.rotate( - transformMapper.get(cameraEntity).rotation );
		camera.translate(
				transformMapper.get(cameraEntity).position.x, 
				transformMapper.get(cameraEntity).position.y
			);
		camera.update();
		return camera;
	}
	
	
	private void drawObjects(List<Entity> entities) {
		for ( Entity e : entities ) {
			if ( e.isActive() && e.isEnabled() ) {
				drawEntity(e);
			}
		}
	}
	
	/**
	 * Override this method to change or add drawing behavior
	 * Default implementation uses the visualSystem for entities with a VisualComponent
	 * @param e
	 */
	protected void drawEntity(Entity e) {
		if ( visualMapper.getSafe(e) != null ) {
			visualSystem.draw(e, batch);
		}
	}
	
	
	/* Draws each camera texture to the screen
		Could be optimized by drawing all the cameras at the same time (in a single batch)
		but it's probably not worth it, unless there are a lot of cameras simultaneously on screen
	 */
	private void drawToScreen(Entity cameraEntity) {
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
