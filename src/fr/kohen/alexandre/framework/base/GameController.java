package fr.kohen.alexandre.framework.base;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;

public abstract class GameController extends Game {
	private Map<String, GameScreen>	screens = new HashMap<String, GameScreen>();
	
	public void setScreen(GameScreen screen) {
		super.setScreen(screen);
		screen.controllerInit();
		screen.setController(this);
	}
	
	public void addScreen(GameScreen screen, String screenName) {
		screens.put(screenName, screen);
	}
	
	public void setScreen(String screenName) {
		if( screens.containsKey(screenName) ) {
			setScreen( screens.get(screenName) );
		} else {
			throw new RuntimeException("Screen " + screenName + " not loaded");
		}
	}

}
