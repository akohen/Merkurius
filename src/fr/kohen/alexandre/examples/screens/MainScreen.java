package fr.kohen.alexandre.examples.screens;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultControlSystem(20) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
	}
	
	@Override
	protected void initialize() {
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100).addToWorld();

		EntityFactoryExamples.newPlayer(world, 1, 100, 50).addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "camera1").addToWorld();
	}

}
