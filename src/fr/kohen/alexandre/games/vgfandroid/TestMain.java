package fr.kohen.alexandre.games.vgfandroid;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class TestMain extends StateBasedGame {

	public TestMain() { super("miniRPG"); }
	
	/*@Override
	public void initStatesList(GameContainer container) throws SlickException {
		GameState gameState = new GameState();
		addState(gameState);
		//enterState(C.MENU_STATE);
	}*/
	
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new TestMain());
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(50);
			container.setShowFPS(true);
			container.setAlwaysRender(true);
			container.start();
		} 
		catch (SlickException e) { e.printStackTrace(); }
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}