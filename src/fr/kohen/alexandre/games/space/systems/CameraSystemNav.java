package fr.kohen.alexandre.games.space.systems;

import org.newdawn.slick.GameContainer;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.systems.interfaces.CameraSystem;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;


public class CameraSystemNav extends EntityProcessingSystem implements CameraSystem {
	protected GameContainer 				container;
	protected ComponentMapper<Transform> 	transformMapper;
	protected Camera						camera;

	@SuppressWarnings("unchecked")
	public CameraSystemNav(GameContainer container) {
		super(Player.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		transformMapper		= new ComponentMapper<Transform>	(Transform.class, world);
		camera				= new Camera(container.getWidth(), container.getHeight());
	}
	
	@Override
	protected void begin() {
	}
	
	@Override
	protected void process(Entity e) {
		Transform playerTransform = transformMapper.get(e);
		camera.setPosition( playerTransform.getLocation().negate().add(camera.getScreenSize().copy().scale(0.5f)) );
		camera.setRotation( -playerTransform.getRotation() );
	}

	@Override
	public Camera getCamera() { return camera; }
	
	
}
