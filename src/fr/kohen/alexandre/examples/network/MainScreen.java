package fr.kohen.alexandre.examples.network;

import java.net.UnknownHostException;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;

public class MainScreen extends GameScreen {


	protected boolean isServer;
	private NetworkExampleSyncSystem syncSystem;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem( new DefaultBox2DSystem() );	
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );
		if( isServer ) {
			syncSystem = world.setSystem( new NetworkExampleSyncSystem(0.2f, 4445, true) );	// An update is sent every 200ms. This is high enough so client-side interpolation can be noticed	
		} else {
			syncSystem = world.setSystem( new NetworkExampleSyncSystem() );	
		}
		syncSystem.addSyncedComponent(Transform.class, SyncSystem.SyncTypes.ServerToClient);
		syncSystem.addSyncedComponent(Velocity.class, SyncSystem.SyncTypes.ServerToClient);
	}
	
	@Override
	protected void initialize() {
		
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100).addToWorld();

		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraWorld1").addToWorld();
		
		if ( isServer ) {
			EntityFactoryExamples.newPlayer(world, 1, 0, 0).addComponent( new Synchronize("player", true) ).addToWorld();
		} else {
			try { ((NetworkExampleSyncSystem) syncSystem).connect("109.21.201.101", 4445); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}


}
