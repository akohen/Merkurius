package fr.kohen.alexandre.examples.canabalt;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.examples.canabalt.systems.SpawnSystem;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.CameraSystemBase;
import fr.kohen.alexandre.framework.systems.base.CollisionSystemBase;
import fr.kohen.alexandre.framework.systems.base.ControlSystemMouse;
import fr.kohen.alexandre.framework.systems.base.GameStateManager;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.base.MovementSystem;
import fr.kohen.alexandre.framework.systems.base.RenderSystemBase;


public class GameState extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;
	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem cameraSystem;
	private EntitySystem spawnSystem;
	private GameStateManager gameState;
	private MapSystemBase mapSystem;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		controlSystem 		= 	systemManager.setSystem(new ControlSystemMouse(gc, 15f, 15f));
		movementSystem 		= 	systemManager.setSystem(new MovementSystem());
		collisionSystem 	= 	systemManager.setSystem(new CollisionSystemBase());
		cameraSystem 		= 	systemManager.setSystem(new CameraSystemBase(gc));
		spawnSystem 		= 	systemManager.setSystem(new SpawnSystem());
		gameState			=	(GameStateManager) systemManager.setSystem(new GameStateManager(gc, sb));
		mapSystem			=	(MapSystemBase) systemManager.setSystem(new MapSystemBase());
		systemManager.initializeAll();

		// Creating the map
		EntityFactoryCana.createMap(world, 1, 16000,600);
		EntityFactoryCana.createPlayer(world, 1, 50, 50);
		
		EntityFactoryCana.createBox(world, 1, 0, 0, 15);
		EntityFactoryCana.createBox(world, 1, 600, 0, 15);
		EntityFactoryCana.createBox(world, 1, 1200, 0, 15);

		EntityFactoryCana.createBox(world, 1, 200, 200, 15);
		EntityFactoryCana.createBox(world, 1, 800, 200, 15);
		
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		if( !gameState.getState().equalsIgnoreCase(C.STATE_INIT) )  {
			cameraSystem		.process();
			renderSystem		.process();
		}
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
		
		if( mapSystem.getCurrentMap() != -1 )
			gameState.setState(C.STATE_PLAYING);
		
		controlSystem	.process();
		collisionSystem	.process();
		movementSystem	.process();
		spawnSystem		.process();
	}

	
	@Override
	public int getID() { return C.GAME_STATE; }
}
