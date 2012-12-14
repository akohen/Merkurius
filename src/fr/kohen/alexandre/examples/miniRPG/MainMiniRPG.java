package fr.kohen.alexandre.examples.miniRPG;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import fr.kohen.alexandre.framework.base.GameMain;
import fr.kohen.alexandre.framework.engine.C;


public class MainMiniRPG extends GameMain {

	public MainMiniRPG() { super("miniRPG"); }
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		initRessources("data/miniRPG/resources.xml");
		GameState gameState = new GameState();
		MenuState menuState = new MenuState();
		addState(menuState);
		addState(gameState);
		enterState(C.MENU_STATE);
	}
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new MainMiniRPG());
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(50);
			container.setShowFPS(true);
			container.setAlwaysRender(true);
			container.start();
		} 
		catch (SlickException e) { e.printStackTrace(); }
	}

}