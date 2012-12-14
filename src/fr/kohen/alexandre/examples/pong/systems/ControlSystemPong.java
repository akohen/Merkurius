package fr.kohen.alexandre.examples.pong.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.systems.base.ControlSystemBase;

public class ControlSystemPong extends ControlSystemBase {

	public ControlSystemPong(GameContainer container, float speedVertical, float speedHorizontal) {
		super(container, speedVertical, speedHorizontal);
	}
	
	protected void process(Entity e) {
		if( !moveRight && !moveLeft && !moveUp && !moveDown )
			velocityMapper.get(e).setSpeed(new Vector2f(0,0));
		super.process(e);
	}

}
