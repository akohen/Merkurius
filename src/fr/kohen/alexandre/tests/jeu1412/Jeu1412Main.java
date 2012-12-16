package fr.kohen.alexandre.tests.jeu1412;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import fr.kohen.alexandre.framework.base.GameMain;
import fr.kohen.alexandre.framework.engine.C;


public class Jeu1412Main extends GameMain {

	public Jeu1412Main() { super("Jeu 1412"); }

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//initRessources();
		GameState gameState = new GameState();
		addState(gameState);
		enterState(C.GAME_STATE);
	}
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Jeu1412Main());
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