package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;


public class Link extends Spatial {

	//private static final int HEIGHT = 28;
	//private static final int WIDTH = 23;
	
	public Link() {
		super();
		color = Color.white;
	}
	
	public void update(int delta) {
		updateAnimation(delta);
	}

	@Override
	public void initalize() {
		setGraphic(ResourceManager.getSpriteSheet("player"));
		duration = 150;
		addAnimation(C.STAND_DOWN, false, 0, 0);
		addAnimation(C.WALK_DOWN, true, 0, 0, 1, 2, 3, 4, 5, 6, 7);
		addAnimation(C.WALK_UP, true, 1, 0, 1, 2, 3, 4, 5, 6, 7);
		addAnimation(C.WALK_RIGHT, true, 2, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_LEFT, true, 3, 0, 1, 2, 3, 4, 5);
		addAnimation(C.STAND_UP, false, 1, 0);
		addAnimation(C.STAND_RIGHT, false, 2, 0);
		addAnimation(C.STAND_LEFT, false, 3, 0);
		currentAnim = C.STAND_DOWN;
	}


}
