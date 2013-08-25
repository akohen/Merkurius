package fr.kohen.alexandre.framework.systems.interfaces;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public interface ScreenSystem {
	public void setScreen(String screenName);
	public void setScreen(GameScreen screen);
	public void addScreen(GameScreen screen, String screenName);
	public GameController getController();
}
