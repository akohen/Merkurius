package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;


public class CameraSystemBase extends EntityProcessingSystem implements CameraSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<SpatialForm> 	spatialFormMapper;
	@Mapper ComponentMapper<Camera> 		cameraMapper;
	protected GameContainer 				container;

	@SuppressWarnings("unchecked")
	public CameraSystemBase(GameContainer container) {
		super( Aspect.getAspectForAll(Camera.class) );
		this.container = container;
	}

	@Override
	public void initialize() {
	}
	
	@Override
	protected void begin() {
	}


	@Override
	protected void process(Entity camera) { 
		
		if( cameraMapper.get(camera).getName().startsWith("cameraFollowPlayer") ) {
			Entity player = world.getManager(TagManager.class).getEntity("player");
			if( player != null ) {
				camera.addComponent(transformMapper.get(player));
			}
		}
		
	}
	
	
	@Override
	public boolean isVisible(Entity e, Entity camera) {
		return isVisible(transformMapper.get(e), spatialFormMapper.get(e).getSpatial(), camera);
	}
	
	
	@Override
	public boolean isVisible(Transform transform, Spatial spatial, Entity camera) {
		if( transformMapper.get(camera).getMapId() == transform.getMapId() ) {
			
			Shape cameraShape = new Rectangle(0,0,cameraMapper.get(camera).getScreenWidth(),cameraMapper.get(camera).getScreenHeight());
			cameraShape.setLocation( transformMapper.get(camera).getLocation().sub(cameraMapper.get(camera).getOffset() ) );
			cameraShape = cameraShape.transform( org.newdawn.slick.geom.Transform.createRotateTransform(
					(float) -Math.toRadians(transformMapper.get(camera).getRotation()),
					transformMapper.get(camera).getLocation().x,
					transformMapper.get(camera).getLocation().y)
				);
			
			Shape entityShape = spatial.getShape();
			entityShape.setLocation( transform.getLocation().sub(spatial.getOffset()) );
			entityShape = entityShape.transform( org.newdawn.slick.geom.Transform.createRotateTransform(
					(float) Math.toRadians(transform.getRotation()),
					transform.getLocation().x,
					transform.getLocation().y)
				);
			
			if( cameraShape.intersects(entityShape) || cameraShape.contains(entityShape) )
				return true;
			else return false;
		}
		else return false;
	}
}
