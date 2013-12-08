package fr.kohen.alexandre.wip.net;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MainScreen extends GameScreen {

	
	@Override
	protected void setSystems() {
		world.setSystem(new DefaultCameraSystem());
		world.setSystem(new DefaultRenderSystem());
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem(new DefaultBox2DSystem());	
		world.setSystem(new DefaultExpirationSystem());
		world.setSystem(new DefaultDebugSystem());
	}
	
	@Override
	protected void initialize() {
		
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100).addToWorld();
		
		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraWorld1").addToWorld();
	}

}
