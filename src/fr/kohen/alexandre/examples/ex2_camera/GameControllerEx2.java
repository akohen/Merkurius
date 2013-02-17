package fr.kohen.alexandre.examples.ex2_camera;

import fr.kohen.alexandre.framework.GameController;
import fr.kohen.alexandre.framework.GameScreen;

public class GameControllerEx2 extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
