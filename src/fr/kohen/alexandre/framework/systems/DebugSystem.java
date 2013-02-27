package fr.kohen.alexandre.framework.systems;

import java.util.Hashtable;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.ICameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.IPhysicsSystem;

/**
 * Updates the entities position according to the velocity.
 * This system must me called after all other systems affecting the entities position (such as control or collision systems for example)
 * 
 * @author Alexandre
 */
public class DebugSystem extends VoidEntitySystem {
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<CameraComponent> 	cameraMapper;
	protected IPhysicsSystem 					physicsSystem;
	protected ICameraSystem 					cameraSystem;
	protected FrameBuffer 						framebuffer;
	protected Box2DDebugRenderer 				debugRenderer;
	protected boolean 							acceptingInput;
	protected boolean 							debugEnabled;
	protected SpriteBatch 						batch;
	protected FPSLogger 						fps;

	public DebugSystem() {
		super();
		batch 			= new SpriteBatch();
		framebuffer 	= new FrameBuffer(Format.RGBA4444, 640, 480, true);
		debugRenderer 	= new Box2DDebugRenderer();
		acceptingInput	= true;
		debugEnabled	= false;
		fps 			= new FPSLogger();
	}

	@Override
	public void initialize() {
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
		physicsSystem	= Systems.get(IPhysicsSystem.class, world);
		cameraSystem	= Systems.get(ICameraSystem.class, world);
	}
	

	@Override
	protected void processSystem() {
		
		// Debug system toggle
		if ( Gdx.input.isKeyPressed( Input.Keys.R ) ) {
			if ( acceptingInput) {
				acceptingInput 	= !acceptingInput;
				debugEnabled	= !debugEnabled;
				if( debugEnabled ) {
					Gdx.app.log("DebugSystem", "Debug enabled");
				} else {
					Gdx.app.log("DebugSystem", "Debug disabled");				
				}
			}
		} else {
			acceptingInput 	= true;
		}
		
		// FPS logger
		if ( debugEnabled ) {
			fps.log();
		}
		
		// Debug rendering
		if ( physicsSystem != null && debugEnabled ) {
			ImmutableBag<Entity> cameras;
			if ( cameraSystem != null ) {
				cameras = cameraSystem.getCameras();
			} else {
				cameras = world.getManager(GroupManager.class).getEntities("CAMERA");
			}
			Hashtable<Integer, World> 	universe 	= physicsSystem.getUniverse();
			OrthographicCamera 			mainCamera 	= new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
			mainCamera.setToOrtho(true);
			
			for ( int i = 0, s = cameras.size(); s > i; i++ ) {
				framebuffer.begin();
				Gdx.gl.glClearColor(1, 1, 1, 0);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				
				Entity cameraEntity = cameras.get(i);
				
				OrthographicCamera camera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
				camera.rotate( - transformMapper.get(cameraEntity).rotation );
				camera.translate(
						transformMapper.get(cameraEntity).x, 
						transformMapper.get(cameraEntity).y
					);
				camera.update();				
				
				for( Integer j : universe.keySet() ) {
					World b2World = universe.get(j);
					if ( transformMapper.get(cameraEntity).mapId == j ) { // Rendering world if the camera is in it
						debugRenderer.render(b2World, camera.combined);					
					}
				}
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
		
	}
	
	
	
}	