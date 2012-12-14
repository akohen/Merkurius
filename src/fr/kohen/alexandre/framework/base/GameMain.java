package fr.kohen.alexandre.framework.base;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import fr.kohen.alexandre.framework.engine.resources.ResourceManager;


public class GameMain extends StateBasedGame {
	protected static boolean ressourcesInited;

	
	public GameMain(String name) {
		super(name);
	}
	

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		/*initRessources("data/resources.xml");
		GameState gameState = new GameState();
		addState(gameState);
		enterState(C.GAME_STATE);*/
	}
	

	public static void initRessources(String file) throws SlickException {
		if (ressourcesInited)
			return;
		try {
			ResourceManager.loadResources(file);
			setVolume(0.5f,0.5f);
		} catch (IOException e) {
			Log.error("failed to load ressource file '"+file+"': " + e.getMessage());
			throw new SlickException("Resource loading failed!");
		}
		ressourcesInited = true;
	}
	
	public static void setVolume(float sfx, float music) {
		ResourceManager.setSfxVolume(sfx);
		ResourceManager.setMusicVolume(music);
		
	}
	

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new GameMain("Game name"));
			container.setDisplayMode(1024, 768, false);
			container.setTargetFrameRate(50);
			container.setShowFPS(true);
			container.setAlwaysRender(true);
			container.start();
		} 
		catch (SlickException e) { e.printStackTrace(); }
	}

}