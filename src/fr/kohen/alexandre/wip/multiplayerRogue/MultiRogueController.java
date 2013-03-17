package fr.kohen.alexandre.wip.multiplayerRogue;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.wip.multiplayerRogue.screens.MainScreen;
import fr.kohen.alexandre.wip.multiplayerRogue.screens.MenuScreen;

public class MultiRogueController extends GameController {
	
	@Override
	public void create() {
		this.addScreen(new MainScreen(true), "serverScreen");
		this.addScreen(new MainScreen(false), "clientScreen");
		this.addScreen(new MenuScreen(), "menuScreen");
		setScreen("menuScreen");
	}

}
