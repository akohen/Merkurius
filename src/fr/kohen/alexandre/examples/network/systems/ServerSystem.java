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
	private int lastPlayerId = 0;
	
	public ServerSystem(float delta, int port) { super(delta, port); }
	
	@Override
	protected void begin() { 
		super.begin();
		
		for( DatagramPacket packet : events ) {
			String message = new String(packet.getData(), 0, packet.getLength());
			String[] data = message.split(" ");
			
			if( data[0].equalsIgnoreCase("connect") ) {
				connectNewPlayer(packet, data);
			} else if( message.startsWith("chat") ) {
				send(message);
			}
		}
	}
	
	private void connectNewPlayer(DatagramPacket packet, String[] data) {
		// Set player id
		int playerId = getPlayerId();
		
		// Create player entity
		Entity player = EntityFactoryExamples.newServerPlayer(world, playerId);
		player.addToWorld();
		
		int clientInPort = Integer.parseInt(data[1]);
		GameClientImpl client = new GameClientImpl(packet.getAddress(), clientInPort, packet.getPort(), playerId, player);
		send(client, "connected player " + client.getId() + " " + player.getId() );
		
		clientList.add( client );
		Gdx.app.log("New connection: ", packet.getAddress().toString() + ":" + packet.getPort() + " " + data[1] );
	}
	
	private int getPlayerId() {
		return lastPlayerId++;
	}


	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper	.getSafe(e);
		EntityState	state		= stateMapper		.getSafe(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Synchronize	sync		= syncMapper		.get(e);

		// Creating the message
		String message = "update " + sync.getType() + " " + e.getId();
		
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
