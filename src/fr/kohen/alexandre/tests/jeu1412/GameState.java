package fr.kohen.alexandre.tests.jeu1412;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.CameraSystemBase;
import fr.kohen.alexandre.framework.systems.base.DebugSystemBase;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.base.MouseSystemBase;
import fr.kohen.alexandre.framework.systems.base.RenderSystemBase;


public class GameState extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;
	private EntitySystem debugSystem;
	private EntitySystem cameraSystem;
	private EntitySystem mouseSystem;

	
	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		debugSystem 		= 	systemManager.setSystem(new DebugSystemBase(gc));
		cameraSystem 		= 	systemManager.setSystem(new CameraSystemBase(gc));
		mouseSystem 		= 	systemManager.setSystem(new MouseSystemBase(gc));
		systemManager.setSystem(new MapSystemBase());
		systemManager.initializeAll();

		// Creating the map
		EntityFactory1412.createMap(world, 1, 16000,600);
		
		EntityFactory1412.createBox(world, 1, 0, 0, 50);
		EntityFactory1412.createBox(world, 1, 0, 75, 50);
		EntityFactory1412.createBox(world, 1, 200, 150, 100);
		EntityFactory1412.createBox(world, 1, 0, 180, 50);
		

		EntityFactory1412.createBox(world, 2, 0, 0, 100);
		
		EntityFactory1412.createPlayer(world, 1, 75, 150, 0);

		EntityFactory1412.createCamera(world, 1, 0, 0, 0, 200, 150, 400, 300, 0, "camera1");
		EntityFactory1412.createCamera(world, 1, 0, 0, 0, 400, 300, 400, 300, 45, "camera2");
		EntityFactory1412.createCamera(world, 2, 0, 0, 0, 600, 150, 400, 300, 0, "camera3");
		EntityFactory1412.createCamera(world, 1, 0, 0, 10, 200, 450, 400, 300, 0, "camera4");
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {

		mouseSystem			.process();
		renderSystem		.process();
		debugSystem			.process();
		cameraSystem		.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {	
		world.loopStart();
		world.setDelta(delta);
	}

	
	@Override
	public int getID() { return C.GAME_STATE; }
}
