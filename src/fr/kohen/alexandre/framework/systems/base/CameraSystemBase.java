package fr.kohen.alexandre.framework.systems.base;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class CameraSystemBase extends EntityProcessingSystem implements CameraSystem {
	protected GameContainer 				container;
	protected ComponentMapper<Transform> 	transformMapper;
	protected List<Entity>					cameras;
	protected MapSystem 					mapSystem;

	@SuppressWarnings("unchecked")
	public CameraSystemBase(GameContainer container) {
		super(Camera.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		cameras				= new ArrayList<Entity>();
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		mapSystem			= Systems.get(MapSystem.class, world);
	}
	
	@Override
	protected void begin() {
	}


	@Override
	protected void process(Entity e) { }

	@Override
	protected void added(Entity e) { cameras.add(e); }
	
	public List<Entity> getCameras() { return cameras; }

}
