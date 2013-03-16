package fr.kohen.alexandre.framework.systems;

import com.artemis.systems.VoidEntitySystem;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.interfaces.ScreenSystem;

public class DefaultScreenSystem extends VoidEntitySystem implements ScreenSystem {

	private GameScreen screen;

	public DefaultScreenSystem(GameScreen screen) {
		this.screen = screen;
	}

	@Override
	protected void processSystem() {
	}
	
	public void setScreen(String screenName) {
		screen.getController().setScreen(screenName);
	}

}
