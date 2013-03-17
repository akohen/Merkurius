package fr.kohen.alexandre.examples.multiplayerRogue;

import fr.kohen.alexandre.examples.multiplayerRogue.screens.MainScreen;
import fr.kohen.alexandre.examples.multiplayerRogue.screens.MenuScreen;
import fr.kohen.alexandre.framework.base.GameController;

public class MultiRogueController extends GameController {
	
	@Override
	public void create() {
		this.addScreen(new MainScreen(true), "serverScreen");
		this.addScreen(new MainScreen(false), "clientScreen");
		this.addScreen(new MenuScreen(), "menuScreen");
		setScreen("menuScreen");
	}

}
