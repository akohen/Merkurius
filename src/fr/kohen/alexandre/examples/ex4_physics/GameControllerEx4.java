package fr.kohen.alexandre.examples.ex4_physics;

import fr.kohen.alexandre.framework.GameController;
import fr.kohen.alexandre.framework.GameScreen;

public class GameControllerEx4 extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
