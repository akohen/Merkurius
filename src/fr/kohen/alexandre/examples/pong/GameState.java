package fr.kohen.alexandre.examples.pong;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

import fr.kohen.alexandre.examples.pong.systems.CollisionSystemPong;
import fr.kohen.alexandre.examples.pong.systems.EnemySystem;
import fr.kohen.alexandre.examples.pong.systems.ScoreSystem;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.systems.base.ControlSystemMouse;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;
import fr.kohen.alexandre.framework.systems.base.MovementSystem;
import fr.kohen.alexandre.framework.systems.base.RenderSystemBase;


public class GameState extends BasicGameState {
	private World world;
	private EntitySystem renderSystem;
	private EntitySystem controlSystem;
	private EntitySystem enemySystem;
	private EntitySystem movementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem scoreSystem;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		world = new World();
		SystemManager systemManager = world.getSystemManager();
		renderSystem 		= 	systemManager.setSystem(new RenderSystemBase(gc));
		controlSystem 		= 	systemManager.setSystem(new ControlSystemMouse(gc, 10f, 0));
		enemySystem 		= 	systemManager.setSystem(new EnemySystem(1f));
		movementSystem 		= 	systemManager.setSystem(new MovementSystem());
		collisionSystem 	= 	systemManager.setSystem(new CollisionSystemPong());
		scoreSystem 		= 	systemManager.setSystem(new ScoreSystem());
		systemManager.setSystem(new MapSystemBase());
		systemManager.initializeAll();

		// Creating the map
		EntityFactoryPong.createMap(world, 1, 800,600);
		EntityFactoryPong.createBall(world, 1, 400, 400);
		
		// Adding players
		EntityFactoryPong.createPlayer(world, 1, 10, 200);
		EntityFactoryPong.createEnemy(world, 1, 775, 200);
		
		// Creating borders
		EntityFactoryPong.createObstacle(world, 1, 0, 0, 800, 1);
		EntityFactoryPong.createObstacle(world, 1, 0, 600, 800, 1);
		
		((ScoreSystem) scoreSystem).restartGame();
		
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		renderSystem		.process();
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		world.loopStart();
		world.setDelta(delta);
		controlSystem	.process();
		enemySystem		.process();
		collisionSystem	.process();
		movementSystem	.process();
		scoreSystem		.process();
	}

	
	@Override
	public int getID() { return C.GAME_STATE; }
}
