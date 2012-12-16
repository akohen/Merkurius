package fr.kohen.alexandre.framework.systems.base;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;

import fr.kohen.alexandre.framework.components.Camera;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class CameraSystemBase extends EntityProcessingSystem implements CameraSystem {
	protected GameContainer 				container;
	protected List<Entity>					cameras;

	@SuppressWarnings("unchecked")
	public CameraSystemBase(GameContainer container) {
		super(Camera.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		cameras				= new ArrayList<Entity>();
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
