package fr.kohen.alexandre.wip.masTest;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.*;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		//world.setSystem( new DefaultAnimationSystem() );
		//world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultDebugSystem() );
		world.setSystem( new MasTestSystem() );	
	}
	
	@Override
	protected void initialize() {
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50).addToWorld();
		
		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 400, 300, 0, "world1").addToWorld();
	}

}
