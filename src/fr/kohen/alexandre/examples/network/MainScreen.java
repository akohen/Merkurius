package fr.kohen.alexandre.examples.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.examples.network.systems.ClientSystem;
import fr.kohen.alexandre.examples.network.systems.ServerSystem;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;

public class MainScreen extends GameScreen {

	protected ClientSystem syncSystem;
	protected boolean isServer;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	@Override
	protected void setSystems() {
		world.setSystem(new DefaultCameraSystem());
		if ( isServer )
			world.setSystem(new DefaultControlSystem(50));
		world.setSystem(new DefaultRenderSystem());
		world.setSystem( new DefaultVisualSystem(EntityFactoryExamples.visuals) );
		world.setSystem(new DefaultBox2DSystem());	
		world.setSystem(new DefaultExpirationSystem());
		world.setSystem(new DefaultDebugSystem());	
		if ( isServer ) {
			world.setSystem(new ServerSystem(0.1f, 4445));	
			Gdx.app.log("MainScreen", "Server mode");
		} else {
			this.syncSystem = world.setSystem(new ClientSystem());	
			Gdx.app.log("MainScreen", "Client mode");	
		}
	}
	
	@Override
	protected void initialize() {
		//EntityFactoryExamples.addLordLard(world, 1, 0, 125);
		
		EntityFactoryExamples.newBox(world, 1, 0, 0, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 75, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 0, 200, 50).addToWorld();
		EntityFactoryExamples.newBox(world, 1, 130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, -100, 100).addToWorld();
		EntityFactoryExamples.newBox(world, 1, -130, 100, 100).addToWorld();
		
		//EntityFactoryExamples.addPlayer(world, 1, 75, 150);
		
		EntityFactoryExamples.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraWorld1").addToWorld();
		
		if ( !isServer ) {
			try { ((SyncSystem) syncSystem).connect(new GameClientImpl(InetAddress.getByName("127.0.0.1"), 4445)); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}

}
