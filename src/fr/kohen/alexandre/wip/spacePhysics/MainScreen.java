package fr.kohen.alexandre.wip.spacePhysics;

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
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactorySpacePhysics.visuals) );
		world.setSystem( new DefaultDebugSystem() );
		
		world.setSystem( new GravitySystem() );
	}
	
	@Override
	protected void initialize() {
		
		EntityFactorySpacePhysics.newPlanet(world, 1, 0, 0).addToWorld();
		EntityFactorySpacePhysics.newSatellite(world, 1, 0, 150, 1000).addToWorld();
		

		EntityFactorySpacePhysics.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "camera1").addToWorld();
	}

}
