package fr.kohen.alexandre.examples.pong.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.base.ControlSystemBase;

public class ControlSystemPong extends ControlSystemBase {
	@Mapper ComponentMapper<Velocity> 		velocityMapper;

	public ControlSystemPong(GameContainer container, float speedVertical, float speedHorizontal) {
		super(container, speedVertical, speedHorizontal);
	}
	
	protected void process(Entity e) {
		if( !moveRight && !moveLeft && !moveUp && !moveDown )
			velocityMapper.get(e).setSpeed(new Vector2f(0,0));
		super.process(e);
	}

}
