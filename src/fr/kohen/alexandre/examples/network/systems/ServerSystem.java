package fr.kohen.alexandre.examples.network.systems;

import java.net.DatagramPacket;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.examples.network.GameClientImpl;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;


public class ServerSystem extends DefaultSyncSystem {
	private int								playerId = 0;
	

	public ServerSystem(float delta, int port) { super(delta, port); }

	@Override
	public void initialize() {
		super.initialize();
	}
	
	
	@Override
	public void receive(DatagramPacket packet) { 
		String message = new String(packet.getData(), 0, packet.getLength());
		String[] data = message.split(" ");
		
		if( data[0].equalsIgnoreCase("connect") ) {
			Entity player = EntityFactoryExamples.newNetworkExamplePlayer(world, 1, 75, 150);
			player.addToWorld();
			GameClientImpl client = new GameClientImpl(packet.getAddress(), Integer.parseInt(data[1]), packet.getPort(), playerId++, player);
			send(client, "connected " + client.getId() + " " + player.getId() );
			clientList.add( client );
			Gdx.app.log("New connection: ", packet.getAddress().toString() + ":" + packet.getPort() + " " + data[1] );
		}
	}



	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.getSafe(e);
		EntityState	state		= stateMapper		.getSafe(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Synchronize	sync		= syncMapper		.get(e);

		// Creating the message
		String message = e.getId() + " " + sync.getType();
		
		if( state != null )
			message += " " + state.getState();
		
		if( transform != null )
			message += " " + transform.position.x + " " + transform.position.y + " " + transform.rotation;
		
		if( velocity != null )
			message += " " + velocity.getX() + " " + velocity.getY() + " " + velocity.getRotation();
		

		// Sending the message for each client
		send(message);
	}

}
