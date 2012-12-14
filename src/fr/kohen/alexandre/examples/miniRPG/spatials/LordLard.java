package fr.kohen.alexandre.examples.miniRPG.spatials;

import org.newdawn.slick.Color;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.Spatial;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;


public class LordLard extends Spatial {
	
	public LordLard() {
		super();
		color = new Color(Color.white);
	}
	
	public void update(int delta) {
		updateAnimation(delta);
	}

	@Override
	public void initalize() {
		setGraphic(ResourceManager.getSpriteSheet("lordLard"));
		duration = 150;
		addAnimation(C.STAND_DOWN, 		false, 0, 0);
		addAnimation(C.STAND_DOWN_LEFT, false, 1, 0);
		addAnimation(C.STAND_LEFT, 		false, 2, 0);
		addAnimation(C.STAND_UP_LEFT, 	false, 3, 0);
		addAnimation(C.STAND_UP, 		false, 4, 0);
		addAnimation(C.STAND_UP_RIGHT, 	false, 5, 0);
		addAnimation(C.STAND_RIGHT, 	false, 6, 0);
		addAnimation(C.STAND_DOWN_RIGHT,false, 7, 0);
		
		addAnimation(C.WALK_DOWN, 		true, 0, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_DOWN_LEFT, 	true, 1, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_LEFT, 		true, 2, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_UP_LEFT, 	true, 3, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_UP, 		true, 4, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_UP_RIGHT, 	true, 5, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_RIGHT, 		true, 6, 0, 1, 2, 3, 4, 5);
		addAnimation(C.WALK_DOWN_RIGHT, true, 7, 0, 1, 2, 3, 4, 5);
		
		currentAnim = C.STAND_DOWN;
	}

}
