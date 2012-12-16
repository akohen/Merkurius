package fr.kohen.alexandre.games.space;

import java.net.UnknownHostException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.ActionsSystemBase;
import fr.kohen.alexandre.framework.systems.base.CameraSystemBase;
import fr.kohen.alexandre.framework.systems.base.CollisionSystemFast;
import fr.kohen.alexandre.framework.systems.base.DebugSystemBase;
import fr.kohen.alexandre.framework.systems.base.FrictionSystem;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.base.MouseSystemBase;
import fr.kohen.alexandre.framework.systems.base.MovementSystemFloat;
import fr.kohen.alexandre.framework.systems.interfaces.CommunicationSystem;
import fr.kohen.alexandre.framework.systems.unused.RenderSystemBase;
import fr.kohen.alexandre.games.space.systems.ApplyDestination;
import fr.kohen.alexandre.games.space.systems.ClientHud;
import fr.kohen.alexandre.games.space.systems.ClientSyncSystem;
import fr.kohen.alexandre.games.space.systems.ServerSyncSystem;

public class GameState extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;
	private EntitySystem debugSystem;
	//private EntitySystem controlSystem;
	private EntitySystem applyDestination;
	private EntitySystem movementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem frictionSystem;
	private EntitySystem syncSystem;
	private EntitySystem mouseSystem;
	private EntitySystem actionsSystem;
	private EntitySystem guiSystem;
	private EntitySystem cameraSystem;
	private	boolean	server;
	
	public GameState(boolean b) { server = b; }


	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		debugSystem 		= 	systemManager.setSystem(new DebugSystemBase(gc));
		//controlSystem 		= 	systemManager.setSystem(new ControlSystemMouseClick(gc));
		applyDestination	= 	systemManager.setSystem(new ApplyDestination());
		movementSystem 		= 	systemManager.setSystem(new MovementSystemFloat());
		collisionSystem 	= 	systemManager.setSystem(new CollisionSystemFast());
		frictionSystem 		= 	systemManager.setSystem(new FrictionSystem(1f));
		if(server)
			syncSystem 		= 	systemManager.setSystem(new ServerSyncSystem(150,4445));
		else {
			syncSystem 		= 	systemManager.setSystem(new ClientSyncSystem());
			guiSystem 		= 	systemManager.setSystem(new ClientHud());
			cameraSystem	= 	systemManager.setSystem(new CameraSystemBase(gc));
		}
		
		mouseSystem			=	systemManager.setSystem(new MouseSystemBase(gc));
		actionsSystem		=	systemManager.setSystem(new ActionsSystemBase(gc));
		systemManager.setSystem(new MapSystemBase());
		systemManager.initializeAll();
		
		EntityFactorySpaceGame.createMap(world, 1, 800, 600);
		EntityFactorySpaceGame.createMouse(world);
		
		if(server) {
			Entity player = EntityFactorySpaceGame.createPlayer(world, 1, 50, 50);
			
			EntityFactorySpaceGame.createBox(world, 1, 250, 250, 50);

			EntityFactorySpaceGame.createArrow(world, 1, 400, 200, "left", player);
			EntityFactorySpaceGame.createArrow(world, 1, 450, 200, "right", player);
		}
		else {
			Log.info("Trying to connect");
			try { ((CommunicationSystem) syncSystem).connect("127.0.0.1", 4445); }
			catch (UnknownHostException e) { e.printStackTrace(); }
			EntityFactorySpaceGame.createArrow(world, 1, 400, 200, "left");
			EntityFactorySpaceGame.createArrow(world, 1, 450, 200, "right");
		}

		
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		if( !server )
			cameraSystem	.process();
		renderSystem		.process();
		debugSystem			.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
		//if(server)
		//controlSystem	.process();
		
		mouseSystem		.process();
		actionsSystem	.process();
		
		applyDestination.process();
		frictionSystem	.process();
		collisionSystem	.process();
		movementSystem	.process();
		
		if( !server )
			guiSystem	.process();
		
		syncSystem		.process();
	}

	
	@Override
	public int getID() { return C.GAME_STATE; }
}
