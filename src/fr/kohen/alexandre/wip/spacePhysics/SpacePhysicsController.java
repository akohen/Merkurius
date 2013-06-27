package fr.kohen.alexandre.wip.spacePhysics;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class SpacePhysicsController extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
