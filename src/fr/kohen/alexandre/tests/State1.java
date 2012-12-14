package fr.kohen.alexandre.tests;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.framework.systems.base.CollisionSystemFast;
import fr.kohen.alexandre.framework.systems.base.DebugSystemBase;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.base.MovementSystemFloat;
import fr.kohen.alexandre.tests.systems.RenderSystemTemp;

public class State1 extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;
	private EntitySystem debugSystem;
	private EntitySystem movementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem cameraSystem;
	
	public State1() {  }


	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemTemp(gc));
		debugSystem 		= 	systemManager.setSystem(new DebugSystemBase(gc));
		movementSystem 		= 	systemManager.setSystem(new MovementSystemFloat());
		collisionSystem 	= 	systemManager.setSystem(new CollisionSystemFast());
		
		systemManager.setSystem(new MapSystemBase());
		systemManager.initializeAll();
		
		EntityFactoryTestGame.createMap(world, 1, 800, 600);
		EntityFactoryTestGame.createMouse(world);
		EntityFactoryTestGame.createPlayer(world, 1, 400, 300);

		
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		cameraSystem	.process();
		renderSystem	.process();
		debugSystem		.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
		
		collisionSystem	.process();
		movementSystem	.process();
		
	}

	
	@Override
	public int getID() { return 1; }
}
