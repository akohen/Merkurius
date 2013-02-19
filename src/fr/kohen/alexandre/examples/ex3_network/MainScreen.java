package fr.kohen.alexandre.examples.ex3_network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples.ex3_network.systems.ClientSystem;
import fr.kohen.alexandre.examples.ex3_network.systems.ServerSystem;
import fr.kohen.alexandre.framework.GameScreen;
import fr.kohen.alexandre.framework.systems.AnimationSystem;
import fr.kohen.alexandre.framework.systems.Box2DSystem;
import fr.kohen.alexandre.framework.systems.CameraSystem;
import fr.kohen.alexandre.framework.systems.ControlSystem;
import fr.kohen.alexandre.framework.systems.DebugSystem;
import fr.kohen.alexandre.framework.systems.ExpirationSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;
import fr.kohen.alexandre.framework.systems.interfaces.ISyncSystem;

public class MainScreen extends GameScreen {

	protected ClientSystem syncSystem;
	protected boolean isServer;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	@Override
	protected void setSystems() {
		world.setSystem(new CameraSystem());
		if ( isServer )
			world.setSystem(new ControlSystem(50));
		world.setSystem(new AnimationSystem());
		world.setSystem(new RenderSystem());
		world.setSystem(new Box2DSystem());	
		world.setSystem(new ExpirationSystem());
		world.setSystem(new DebugSystem());	
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
		//EntityFactoryEx3.addLordLard(world, 1, 0, 125);
		
		EntityFactoryEx3.createBox(world, 1, 0, 0, 50);
		EntityFactoryEx3.createBox(world, 1, 0, 75, 50);
		EntityFactoryEx3.createBox(world, 1, 0, 200, 50);
		EntityFactoryEx3.createBox(world, 1, 130, -100, 100);
		EntityFactoryEx3.createBox(world, 1, -130, -100, 100);
		EntityFactoryEx3.createBox(world, 1, -130, 100, 100);
		
		//EntityFactoryEx3.addPlayer(world, 1, 75, 150);
		
		EntityFactoryEx3.addCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraWorld1");
		
		if ( !isServer ) {
			try { ((ISyncSystem) syncSystem).connect(new GameClientImpl(InetAddress.getByName("127.0.0.1"), 4445)); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}

}
