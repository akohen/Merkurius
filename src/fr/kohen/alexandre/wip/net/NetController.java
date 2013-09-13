package fr.kohen.alexandre.wip.net;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class NetController extends GameController {
	
	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
