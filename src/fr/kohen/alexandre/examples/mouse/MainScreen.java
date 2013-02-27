package fr.kohen.alexandre.examples.mouse;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.ActionSystem;
import fr.kohen.alexandre.framework.systems.Box2DSystem;
import fr.kohen.alexandre.framework.systems.CameraSystem;
import fr.kohen.alexandre.framework.systems.DebugSystem;
import fr.kohen.alexandre.framework.systems.MouseSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem(new CameraSystem());
		world.setSystem(new RenderSystem());
		world.setSystem(new Box2DSystem());
		world.setSystem(new DebugSystem());	
		world.setSystem(new MouseSystem());	
		world.setSystem(new ActionSystem());	
	}
	
	@Override
	protected void initialize() {
		
		EntityFactoryMouse.createBox(world, 1, 0, 0, 50);
		EntityFactoryMouse.createBox(world, 1, 0, 75, 50);
		EntityFactoryMouse.createBox(world, 1, 0, 200, 50);
		EntityFactoryMouse.createBox(world, 1, 130, -100, 100);
		EntityFactoryMouse.createBox(world, 1, -130, -100, 100);
		EntityFactoryMouse.createBox(world, 1, -130, 100, 100);

		EntityFactoryMouse.addPlayer(world, 1, 100, 50);

		EntityFactoryMouse.addCamera(world, 1, 100, 50, 0, 100, 50, 400, 300, 45, "cameraNRotationTest");
	}

}
