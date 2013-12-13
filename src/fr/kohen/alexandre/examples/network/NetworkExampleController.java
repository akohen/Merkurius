package fr.kohen.alexandre.examples.network;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class NetworkExampleController extends GameController {

	private boolean isServer;


	public NetworkExampleController(boolean server) {
		this.isServer = server;
	}
	
	
	@Override
	public void create() {
		GameScreen screen = new MainScreen(isServer);
		setScreen(screen);
	}

}
