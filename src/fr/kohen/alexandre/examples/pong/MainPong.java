package fr.kohen.alexandre.examples.pong;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import fr.kohen.alexandre.framework.base.GameMain;
import fr.kohen.alexandre.framework.engine.C;


public class MainPong extends GameMain {

	public MainPong() { super("Pong"); }
	

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//initRessources();
		GameState gameState = new GameState();
		addState(gameState);
		enterState(C.GAME_STATE);
	}

	

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new MainPong());
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