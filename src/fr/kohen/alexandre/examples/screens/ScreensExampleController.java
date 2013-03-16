package fr.kohen.alexandre.examples.screens;

import fr.kohen.alexandre.framework.base.GameController;

public class ScreensExampleController extends GameController {

	@Override
	public void create() {
		this.addScreen(new MenuScreen(), "menuScreen");
		this.addScreen(new MainScreen(), "mainScreen");
		this.setScreen("menuScreen");
	}

}
