package fr.kohen.alexandre.examples.text;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class TextExampleController extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
