package fr.kohen.alexandre.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import fr.kohen.alexandre.framework.base.GameMain;


public class TestGame extends GameMain {

	public TestGame() { super("Space Game Server"); }
	

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		State1 state1 = new State1();
		addState(state1);
		enterState(1);
	}

	

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new TestGame());
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