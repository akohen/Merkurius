package fr.kohen.alexandre.framework.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.*;
import fr.kohen.alexandre.framework.systems.unused.RenderSystemBase;


@SuppressWarnings("unused")
public class GameState extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		systemManager.initializeAll();
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		renderSystem		.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
	}

	
	@Override
	public int getID() { return C.GAME_STATE; }
}
