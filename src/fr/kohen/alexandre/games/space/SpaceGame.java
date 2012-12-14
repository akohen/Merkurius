package fr.kohen.alexandre.games.space;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import fr.kohen.alexandre.framework.base.GameMain;
import fr.kohen.alexandre.framework.engine.C;


public class SpaceGame extends GameMain {

	public SpaceGame() { super("Space Game Server"); }
	

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		GameState gameState = new GameState(true);
		addState(gameState);
		enterState(C.GAME_STATE);
	}

	

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new SpaceGame());
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(50);
			container.setShowFPS(false);
			container.setAlwaysRender(true);
			container.setMouseGrabbed(false);
			container.start();
		} 
		catch (SlickException e) { e.printStackTrace(); }
	}

}