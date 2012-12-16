package fr.kohen.alexandre.examples.miniRPG;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.GameStateManager;
import fr.kohen.alexandre.framework.systems.base.MenuSystemBase;
import fr.kohen.alexandre.framework.systems.interfaces.MenuSystem;
import fr.kohen.alexandre.framework.systems.base.RenderSystemBase;


public class MenuState extends BasicGameState {
	private World world;
	private EntitySystem menuSystem;
	private EntitySystem renderSystem;
	private EntitySystem gameStateManager;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		
		SystemManager systemManager = world.getSystemManager();
		menuSystem 			= 	systemManager.setSystem(new MenuSystemBase(gc));
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		gameStateManager 	= 	systemManager.setSystem(new GameStateManager(gc,sb));
		systemManager.initializeAll();
		
		EntityFactory.createButton(world, 100, 100, "Play", "Main Menu", C.ACTION_ENTERGAMESTATE);
		EntityFactory.createButton(world, 100, 150, "Exit", "Main Menu", C.ACTION_EXIT);
		((MenuSystem) menuSystem).setActiveMenu("Main Menu");
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		menuSystem	.process();
		renderSystem.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
		gameStateManager.process();
	}

	
	@Override
	public int getID() { return C.MENU_STATE; }
}
