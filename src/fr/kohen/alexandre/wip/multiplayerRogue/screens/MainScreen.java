package fr.kohen.alexandre.wip.multiplayerRogue.screens;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.*;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;
import fr.kohen.alexandre.wip.multiplayerRogue.MultiRogueFactory;
import fr.kohen.alexandre.wip.multiplayerRogue.MultiRogueGameClient;
import fr.kohen.alexandre.wip.multiplayerRogue.systems.*;

public class MainScreen extends GameScreen {

	protected ClientSystem syncSystem;
	protected boolean isServer;

	public MainScreen(boolean server) {
		this.isServer = server;
	}
	
	@Override
	protected void setSystems() {
		if ( isServer ) {
			world.setSystem( new ServerSystem(0.1f, 4445));	
			Gdx.app.log("MainScreen", "Server mode");
			Gdx.graphics.setTitle("MultiRogue Server");
		} else {
			this.syncSystem = world.setSystem(new ClientSystem());	
			Gdx.app.log("MainScreen", "Client mode");	
			Gdx.graphics.setTitle("MultiRogue Client");
		}
		if ( isServer )
			world.setSystem( new ServerControlSystem());
		else
			world.setSystem( new ControlSystem());

		world.setSystem( new MultiRogueChat());
		world.setSystem( new MultiRogueCamera());
		world.setSystem( new DefaultRenderSystem());
		world.setSystem( new DefaultVisualSystem(MultiRogueFactory.visuals) );
		world.setSystem( new DefaultTextSystem() );
		world.setSystem( new MultiRoguePhysics());	
		world.setSystem( new DefaultExpirationSystem());
		world.setSystem( new DefaultDebugSystem());
		world.setSystem( new MultiRogueVisibilitySystem());
		
		
	}
	
	@Override
	protected void initialize() {
		MultiRogueFactory.newBox(world, 1, 0, 75, 50).addToWorld();
		MultiRogueFactory.newBox(world, 1, 0, 200, 50).addToWorld();
		MultiRogueFactory.newBox(world, 1, 130, -100, 100).addToWorld();
		MultiRogueFactory.newBox(world, 1, -130, -100, 100).addToWorld();
		MultiRogueFactory.newBox(world, 1, -130, 100, 100).addToWorld();

		MultiRogueFactory.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "cameraFollowPlayer").addToWorld();
		MultiRogueFactory.newCamera(world, -1, 90, 0, 0, -200, -150, 200, 150, 0, "cameraChatBox").addToWorld();
		MultiRogueFactory.newCamera(world, -2, 90, -10, 0, -200, -220, 200, 20, 0, "cameraChatText").addToWorld();
		
		if ( !isServer ) {
			try { ((SyncSystem) syncSystem).connect(new MultiRogueGameClient(InetAddress.getByName("127.0.0.1"), 4445)); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}

}
