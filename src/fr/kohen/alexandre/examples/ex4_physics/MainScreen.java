package fr.kohen.alexandre.examples.ex4_physics;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.CameraSystem;
import fr.kohen.alexandre.framework.systems.ControlSystem;
import fr.kohen.alexandre.framework.systems.DebugSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem(new CameraSystem());
		world.setSystem(new ControlSystem(50));
		world.setSystem(new RenderSystem());
		world.setSystem(new PhysicsSystem());
		world.setSystem(new DebugSystem());	
		world.setSystem(new CollisionSystem());
	}
	
	@Override
	protected void initialize() {
		
		EntityFactoryEx4.createBox(world, 1, 0, 0, 50);
		EntityFactoryEx4.createBox(world, 1, 0, 75, 50);
		EntityFactoryEx4.createBox(world, 1, 0, 200, 50);
		EntityFactoryEx4.createBox(world, 1, 130, -100, 100);
		EntityFactoryEx4.createBox(world, 1, -130, -100, 100);
		EntityFactoryEx4.createBox(world, 1, -130, 100, 100);
		
		EntityFactoryEx4.addPlayer(world, 1, 0, 150);

		EntityFactoryEx4.addCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraFollowPlayer");
	}

}
