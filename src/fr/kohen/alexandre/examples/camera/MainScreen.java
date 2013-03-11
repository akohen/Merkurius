package fr.kohen.alexandre.examples.camera;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.examples._common.ExampleVisuals;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultAnimationSystem;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultAnimationSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(ExampleVisuals.visuals) );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultMouseSystem() );	
	}
	
	@Override
	protected void initialize() {
		EntityFactoryExamples.newLordLard(world, 1, 0, 125).addToWorld();
		
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100).addToWorld();
		

		EntityFactoryExamples.newBox(world, 2, -75, 100, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 2, 0, 100, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 2, 0, 0, 100).addToWorld();
		
		//EntityFactoryExamples.newPlayer(world, 1, 75, 150).addToWorld();

		EntityFactoryExamples.newCamera(world, 2, -50, 50, 0, 160, 120, 320, 240, 0, "world2").addToWorld();
		EntityFactoryExamples.newCamera(world, 2, 0, 100, 0, 160, -120, 160, 120, 0, "cameraRotationTest").addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, -160, 120, 320, 240, 0, "cameraFollowPlayer").addToWorld();
		EntityFactoryExamples.testCamera(world, 1, 0, 0, 0, -160, -120, 320, 240, 0, "testCamera").addToWorld();
	}

}
