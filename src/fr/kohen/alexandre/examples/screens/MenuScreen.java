package fr.kohen.alexandre.examples.screens;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.*;

public class MenuScreen extends GameScreen {

	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem( new TestScreenSystem(this) );
	}
	
	@Override
	protected void initialize() {
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "camera1").addToWorld();
	}

}
