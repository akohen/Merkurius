 package fr.kohen.alexandre.examples.canabalt;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.kohen.alexandre.framework.engine.C;


public class MainCana extends StateBasedGame {

	public MainCana() { super("Canabalt-like"); }

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//initRessources();
		GameState gameState = new GameState();
		addState(gameState);
		enterState(C.GAME_STATE);
	}
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new MainCana());
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(50);
			container.setShowFPS(true);
			container.setAlwaysRender(true);
			container.setMouseGrabbed(false);
			container.start();
		} 
		catch (SlickException e) { e.printStackTrace(); }
	}

}