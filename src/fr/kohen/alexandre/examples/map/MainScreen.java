package fr.kohen.alexandre.examples.map;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem(new DefaultCameraSystem());
		world.setSystem(new DefaultRenderSystem());
		world.setSystem(new DefaultBox2DSystem());
		world.setSystem(new DefaultDebugSystem());	
		world.setSystem(new DefaultMouseSystem());	
		world.setSystem(new MapSystemTest());	
	}
	
	@Override
	protected void initialize() {
		
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50);
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50);
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50);
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100);
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100);
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100);

		EntityFactoryExamples.newPlayer(world, 1, 100, 50);

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 400, 300, 0, "camera");
		EntityFactoryExamples.newMap(world, 1, "data/examples/maps/map1.tmx");
	}

}
