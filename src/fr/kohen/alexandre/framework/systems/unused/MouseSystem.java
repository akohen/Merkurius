package fr.kohen.alexandre.framework.systems.unused;

import org.newdawn.slick.GameContainer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Mouse;

public class MouseSystem extends EntityProcessingSystem {
	protected ComponentMapper<Transform> transformMapper;
	protected GameContainer container;

	@SuppressWarnings("unchecked")
	public MouseSystem(GameContainer container) {
		super(Mouse.class, Transform.class);
		this.container = container;
	}

	@Override
	public void initialize() {
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
	}

	@Override
	protected void process(Entity e) {
		Transform 	transform 	= transformMapper.get(e);
		transform.setX( container.getInput().getMouseX() );
		transform.setY( container.getInput().getMouseY() );
	}
}	