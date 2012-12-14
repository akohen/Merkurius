package fr.kohen.alexandre.games.space;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import fr.kohen.alexandre.framework.base.GameMain;
import fr.kohen.alexandre.framework.engine.C;


public class SpaceGameClient extends GameMain {

	public SpaceGameClient() { super("Space Game Client"); }
	

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		GameState gameState = new GameState(false);
		addState(gameState);
		enterState(C.GAME_STATE);
	}

	

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new SpaceGameClient());
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