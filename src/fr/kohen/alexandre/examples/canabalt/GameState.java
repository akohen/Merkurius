package fr.kohen.alexandre.examples.canabalt;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.World;

import fr.kohen.alexandre.examples.canabalt.systems.SpawnSystem;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.CameraSystemBase;
import fr.kohen.alexandre.framework.systems.base.CollisionSystemBase;
import fr.kohen.alexandre.framework.systems.base.GameStateManager;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.base.MovementSystem;
import fr.kohen.alexandre.framework.systems.base.RenderSystemBase;
import fr.kohen.alexandre.framework.systems.unused.ControlSystemMouse;


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
		renderSystem 		= 	world.setSystem(new RenderSystemBase(gc));
		controlSystem 		= 	world.setSystem(new ControlSystemMouse(gc, 15f, 15f));
		movementSystem 		= 	world.setSystem(new MovementSystem());
		collisionSystem 	= 	world.setSystem(new CollisionSystemBase());
		cameraSystem 		= 	world.setSystem(new CameraSystemBase(gc));
		spawnSystem 		= 	world.setSystem(new SpawnSystem());
		gameState			=	(GameStateManager) world.setSystem(new GameStateManager(gc, sb));
		mapSystem			=	(MapSystemBase) world.setSystem(new MapSystemBase());
		world.initialize();

		// Creating the map
		EntityFactoryCana.createMap(world, 1, 16000,600);
		EntityFactoryCana.createPlayer(world, 1, 50, 50);
		
		EntityFactoryCana.createBox(world, 1, 50, 50, 15);
		EntityFactoryCana.createCamera(world, 1, 0, 0, 0, 0, 400, 300, 0, 0, "camera1");
		
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
