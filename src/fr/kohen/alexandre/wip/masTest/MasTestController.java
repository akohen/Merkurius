package fr.kohen.alexandre.wip.masTest;

import fr.kohen.alexandre.framework.base.GameController;

public class MasTestController extends GameController {

	@Override
	public void create() {
		this.addScreen(new MainScreen(), "mainScreen");
		this.setScreen("mainScreen");
	}

}
