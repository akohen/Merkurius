package fr.kohen.alexandre.examples.ex2;

import fr.kohen.alexandre.framework.GameScreen;
import fr.kohen.alexandre.framework.systems.Box2DSystem;
import fr.kohen.alexandre.framework.systems.CameraSystem;
import fr.kohen.alexandre.framework.systems.ControlSystem;
import fr.kohen.alexandre.framework.systems.MovementSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {
		world.setSystem(new CameraSystem());
		world.setSystem(new ControlSystem(1));
		world.setSystem(new RenderSystem());
		world.setSystem(new Box2DSystem());	
		world.setSystem(new MovementSystem());
	}
	
	@Override
	protected void addEntities() {
		//EntityFactoryEx2.addLogo(world, 1, 0, 0);
		
		EntityFactoryEx2.createBox(world, 1, 0, 0, 50);
		EntityFactoryEx2.createBox(world, 1, 0, 75, 50);
		EntityFactoryEx2.createBox(world, 1, 200, 150, 100);
		EntityFactoryEx2.createBox(world, 1, 0, 180, 50);
		

		//EntityFactoryEx2.createBox(world, 2, 0, 0, 100);
		
		EntityFactoryEx2.addPlayer(world, 1, 75, 150);

		//EntityFactoryEx2.addCamera(world, 1, 0, 0, 0, 0, 0, 320, 240, 10, "test");
		EntityFactoryEx2.addCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "test");
		//EntityFactoryEx2.addCamera(world, 1, 0, 100, 0, -100, 0, 640, 480, 0, "test2");
		
		//EntityFactoryEx2.addCamera(world, 1, 0, 0, 0, 200, 150, 400, 300, 0, "camera1");
		//EntityFactoryEx2.addCamera(world, 1, 0, 0, 0, 400, 300, 400, 300, 45, "camera2");
		//EntityFactoryEx2.addCamera(world, 2, 0, 0, 0, 600, 150, 400, 300, 0, "camera3");
		//EntityFactoryEx2.addCamera(world, 1, 0, 0, 10, 200, 450, 400, 300, 0, "camera4");
	}

}
