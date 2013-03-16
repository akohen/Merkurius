package fr.kohen.alexandre.examples.multiplayerRogue;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class MultiRogueController extends GameController {

	private boolean isServer;


	public MultiRogueController(boolean server) {
		this.isServer = server;
	}
	
	
	@Override
	public void create() {
		GameScreen screen = new MainScreen(isServer);
		setScreen(screen);
	}

}
