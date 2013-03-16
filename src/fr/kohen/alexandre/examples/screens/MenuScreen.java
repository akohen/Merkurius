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
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem( new DefaultScreenSystem(this) );	
		world.setSystem( new DefaultMouseSystem() );
		world.setSystem( new DefaultActionSystem(EntityFactoryExamples.actions) );	
	}
	
	@Override
	protected void initialize() {
		EntityFactoryExamples.newScreenExampleButton(world, 1, 0, 0).addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "camera1").addToWorld();
	}

}
