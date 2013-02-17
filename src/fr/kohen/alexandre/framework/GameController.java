package fr.kohen.alexandre.framework;

import com.badlogic.gdx.Game;

public abstract class GameController extends Game {
	public void setScreen (GameScreen screen) {
		super.setScreen(screen);
		screen.controllerInit();
	}

}
