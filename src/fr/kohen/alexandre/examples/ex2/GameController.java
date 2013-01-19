package fr.kohen.alexandre.examples.ex2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class GameController extends Game {

	@Override
	public void create() {
		Screen screen = new MainScreen();
		setScreen(screen);
	}

}
