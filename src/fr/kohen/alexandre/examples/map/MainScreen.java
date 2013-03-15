package fr.kohen.alexandre.examples.map;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.*;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultAnimationSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultDebugSystem() );
		world.setSystem( new MapSystemTest() );	
	}
	
	@Override
	protected void initialize() {
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100).addToWorld();
		
		EntityFactoryExamples.newPlayer(world, 1, 75, 150).addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 400, 300, 0, "world1").addToWorld();
		
		EntityFactoryExamples.newMap(world, 1, "data/examples/maps/map1.tmx").addToWorld();
	}

}
