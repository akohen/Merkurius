package fr.kohen.alexandre.framework.systems;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.ICameraSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraSystem extends EntityProcessingSystem implements ICameraSystem {
	protected ComponentMapper<CameraComponent> 	cameraMapper;
	protected ComponentMapper<Transform> 		transformMapper;

	@SuppressWarnings("unchecked")
	public CameraSystem() {
		super( Aspect.getAspectForAll(CameraComponent.class) );
	}

	@Override
	public void initialize() {
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		cameraMapper 	= ComponentMapper.getFor(CameraComponent.class, world);
	}
	
	@Override
	protected void begin() {
	}


	@Override
	protected void process(Entity camera) { 
		if( cameraMapper.get(camera).name.startsWith("cameraFollowPlayer") ) {
			Entity player = world.getManager(TagManager.class).getEntity("player");
			if( player != null ) {
				transformMapper.get(camera).mapId = transformMapper.get(player).mapId;
				transformMapper.get(camera).setLocation( transformMapper.get(player).getLocation() );
			}
		}
		
	}
	
	
	@Override
	public boolean isVisible(Entity e, Entity camera) {
		return isVisible(transformMapper.get(e), camera);
	}
	
	
	@Override
	public boolean isVisible(Transform transform, Entity camera) {
		if( transformMapper.get(camera).mapId == transform.mapId ) {			
			return true;
		}
		else return false;
	}
	
	
	@Override
	public Camera setCamera(Entity e) {
		OrthographicCamera camera = new OrthographicCamera( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		camera.translate( 
				transformMapper.get(e).x - cameraMapper.get(e).position.x, 
				transformMapper.get(e).y - cameraMapper.get(e).position.y
			);
		camera.rotate( cameraMapper.get(e).rotation - transformMapper.get(e).rotation );
		camera.zoom = cameraMapper.get(e).zoom;
		camera.update();
		
		setWorldClip(
				transformMapper.get(e).x - cameraMapper.get(e).size.x/2,
				transformMapper.get(e).y - cameraMapper.get(e).size.y/2,
				cameraMapper.get(e).size.x,
				cameraMapper.get(e).size.y
			);
		return camera;
	}
	
	
	@Override
	public void resetCamera() {	
		resetWorldClip();
	}
	
	
	private void setWorldClip(float x, float y, float width, float height) {
		DoubleBuffer worldClip = BufferUtils.createDoubleBuffer(4);
		
		Gdx.gl.glEnable(GL11.GL_CLIP_PLANE0);
		worldClip.put(1).put(0).put(0).put(-x).flip();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, worldClip);
		
		Gdx.gl.glEnable(GL11.GL_CLIP_PLANE1);
		worldClip.put(-1).put(0).put(0).put(x + width).flip();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE1, worldClip);
		
		Gdx.gl.glEnable(GL11.GL_CLIP_PLANE2);
		worldClip.put(0).put(1).put(0).put(-y).flip();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE2, worldClip);
		
		Gdx.gl.glEnable(GL11.GL_CLIP_PLANE3);
		worldClip.put(0).put(-1).put(0).put(y + height).flip();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE3, worldClip);
	}
	
	private void resetWorldClip() {
		Gdx.gl.glDisable(GL11.GL_CLIP_PLANE0);
		Gdx.gl.glDisable(GL11.GL_CLIP_PLANE1);
		Gdx.gl.glDisable(GL11.GL_CLIP_PLANE2);
		Gdx.gl.glDisable(GL11.GL_CLIP_PLANE3);
	}
}
