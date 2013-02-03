package fr.kohen.alexandre.framework.systems;

import fr.kohen.alexandre.framework.components.CameraComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.ICameraSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;

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
				camera.addComponent(transformMapper.get(player));
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
}
