package fr.kohen.alexandre.examples.ex2_camera;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.AnimationSystem;
import fr.kohen.alexandre.framework.systems.Box2DSystem;
import fr.kohen.alexandre.framework.systems.CameraSystem;
import fr.kohen.alexandre.framework.systems.ControlSystem;
import fr.kohen.alexandre.framework.systems.DebugSystem;
import fr.kohen.alexandre.framework.systems.ExpirationSystem;
import fr.kohen.alexandre.framework.systems.MouseSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem(new CameraSystem());
		world.setSystem(new ControlSystem(50));
		world.setSystem(new AnimationSystem());
		world.setSystem(new RenderSystem());
		world.setSystem(new Box2DSystem());
		world.setSystem(new ExpirationSystem());
		world.setSystem(new DebugSystem());	
		world.setSystem(new MouseSystem());	
	}
	
	@Override
	protected void initialize() {
		EntityFactoryEx2.addLordLard(world, 1, 0, 125);
		
		EntityFactoryEx2.createBox(world, 1, 0, 0, 50);
		EntityFactoryEx2.createBox(world, 1, 0, 75, 50);
		EntityFactoryEx2.createBox(world, 1, 0, 200, 50);
		EntityFactoryEx2.createBox(world, 1, 130, -100, 100);
		EntityFactoryEx2.createBox(world, 1, -130, -100, 100);
		EntityFactoryEx2.createBox(world, 1, -130, 100, 100);
		

		EntityFactoryEx2.createBox(world, 2, -75, 100, 50);
		EntityFactoryEx2.createBox(world, 2, 0, 100, 50);
		EntityFactoryEx2.createBox(world, 2, 0, 0, 100);
		
		EntityFactoryEx2.addPlayer(world, 1, 75, 150);

		EntityFactoryEx2.addCamera(world, 2, -50, 50, 0, 160, 120, 320, 240, 0, "world2");
		EntityFactoryEx2.addCamera(world, 2, 0, 100, 0, 160, -120, 160, 120, 0, "cameraRotationTest");

		EntityFactoryEx2.addCamera(world, 1, 0, 0, 0, -160, 120, 320, 240, 0, "cameraFollowPlayer");
		EntityFactoryEx2.testCamera(world, 1, 0, 0, 0, -160, -120, 320, 240, 0, "testCamera");
	}

}
