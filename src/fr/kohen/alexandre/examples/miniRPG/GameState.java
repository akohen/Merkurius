package fr.kohen.alexandre.examples.miniRPG;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.examples.miniRPG.systems.MapSystemMiniRPG;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.AnimationSystem;
import fr.kohen.alexandre.framework.systems.base.CollisionSystemFast;
import fr.kohen.alexandre.framework.systems.base.ControlSystemBase;
import fr.kohen.alexandre.framework.systems.base.DebugSystemBase;
import fr.kohen.alexandre.framework.systems.base.FrictionSystem;
import fr.kohen.alexandre.framework.systems.base.CameraSystemBase;
import fr.kohen.alexandre.framework.systems.base.MovementSystemFloat;
import fr.kohen.alexandre.framework.systems.base.RenderSystemBase;


public class GameState extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;
	private EntitySystem debugSystem;
	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem frictionSystem;
	private EntitySystem collisionSystem;
	private EntitySystem animationSystem;
	//private EntitySystem scriptSystem;
	private EntitySystem cameraSystem;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		debugSystem 		= 	systemManager.setSystem(new DebugSystemBase(gc));
		cameraSystem 		= 	systemManager.setSystem(new CameraSystemBase(gc));
		controlSystem 		= 	systemManager.setSystem(new ControlSystemBase(gc, 1.0f));
		movementSystem 		= 	systemManager.setSystem(new MovementSystemFloat());
		frictionSystem 		= 	systemManager.setSystem(new FrictionSystem(0.5f));
		collisionSystem 	= 	systemManager.setSystem(new CollisionSystemFast());
		animationSystem 	= 	systemManager.setSystem(new AnimationSystem());
		//scriptSystem 		= 	systemManager.setSystem(new ScriptSystemMiniRPG(gc));
		systemManager.setSystem(new MapSystemMiniRPG());
		systemManager.initializeAll();

		EntityFactoryMiniRPG.createMap(world, 1, "map1");
		EntityFactoryMiniRPG.createPlayer(world, 1, 200, 200);
		EntityFactoryMiniRPG.createCamera(world, 1, 32, 0, 0, 400, 300, 800, 600, 0, "cameraFollowPlayer");
		
		// Creating an empty level and placing a box in it
		EntityFactoryMiniRPG.createMap(world, 2, 2000,2000);
		EntityFactoryMiniRPG.createBox(world, 2, 200, 200, 20);
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		animationSystem		.process();
		cameraSystem		.process();
		renderSystem		.process();
		debugSystem			.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
		controlSystem	.process();
		frictionSystem	.process();
		collisionSystem	.process();
		movementSystem	.process();
		//scriptSystem	.process();
	}

	
	@Override
	public int getID() { return C.GAME_STATE; }
}
