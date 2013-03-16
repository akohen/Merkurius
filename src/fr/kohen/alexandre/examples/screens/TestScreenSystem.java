package fr.kohen.alexandre.examples.screens;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.interfaces.ScreenSystem;

public class TestScreenSystem extends VoidEntitySystem implements ScreenSystem {

	private GameScreen screen;

	public TestScreenSystem(GameScreen screen) {
		this.screen = screen;
	}

	@Override
	protected void processSystem() {
		if ( Gdx.input.isKeyPressed(Keys.ENTER) ) {
			setScreen("mainScreen");
		}
	}
	
	public void setScreen(String screenName) {
		screen.getController().setScreen(screenName);
	}

}
