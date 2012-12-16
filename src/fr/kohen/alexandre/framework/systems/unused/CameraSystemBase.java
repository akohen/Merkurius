package fr.kohen.alexandre.framework.systems.unused;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Unused;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class CameraSystemBase extends EntityProcessingSystem {
	protected GameContainer 				container;
	protected ComponentMapper<Transform> 	transformMapper;
	protected Camera						camera;
	protected MapSystem 					mapSystem;

	@SuppressWarnings("unchecked")
	public CameraSystemBase(GameContainer container) {
		super(Unused.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		camera 				= new Camera(container.getWidth(), container.getHeight());
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		mapSystem			= Systems.get(MapSystem.class, world);
	}
	
	@Override
	protected void begin() {
		Vector2f shift = new Vector2f(0,0);
		if( mapSystem != null && world.getTagManager().getEntity("player") != null ) {
			Vector2f mapDimension = mapSystem.getMapDimensions();
			Transform playerTransform = transformMapper.get(world.getTagManager().getEntity("player"));
			shift.x = -Math.min(mapDimension.x - container.getWidth(), Math.max(0, playerTransform.getLocation().x - container.getWidth()/2) );
			shift.y = -Math.min(mapDimension.y - container.getHeight(), Math.max(0, playerTransform.getLocation().y - container.getHeight()/2) );
		}
		camera.setPosition(shift);
	}

	public Camera getCamera() { return camera; }

	@Override
	protected void process(Entity e) { }
	

}
