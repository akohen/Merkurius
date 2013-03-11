package fr.kohen.alexandre.examples.mouse;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.examples._common.ExampleVisuals;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultActionSystem;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(ExampleVisuals.visuals) );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultMouseSystem() );	
		world.setSystem( new DefaultActionSystem() );	
	}
	
	@Override
	protected void initialize() {
		
		EntityFactoryExamples.newMouseExampleBox(world, 1, 0, 0, 50).addToWorld();
		EntityFactoryExamples.newMouseExampleBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newMouseExampleBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newMouseExampleBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newMouseExampleBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newMouseExampleBox(world, 1, -130, 100, 100).addToWorld();

		EntityFactoryExamples.newMouseExamplePlayer(world, 1, 100, 50).addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 100, 50, 0, 100, 50, 400, 300, 45, "cameraNoRotationTest").addToWorld();
	}

}
