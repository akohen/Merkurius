package fr.kohen.alexandre.examples.mouse;

import fr.kohen.alexandre.framework.GameController;
import fr.kohen.alexandre.framework.GameScreen;

public class MouseExampleGameController extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
