package fr.kohen.alexandre.examples.ex3_network;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class GameControllerEx3 extends GameController {

	private boolean isServer;


	public GameControllerEx3(boolean server) {
		this.isServer = server;
	}
	
	
	@Override
	public void create() {
		GameScreen screen = new MainScreen(isServer);
		setScreen(screen);
	}

}
