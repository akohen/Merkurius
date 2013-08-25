package fr.kohen.alexandre.examples.network.systems;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.examples.network.GameClientImpl;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;


public class ServerSystem extends DefaultSyncSystem {
	public ServerSystem(float delta, int port) { super(delta, port); }
	private ComponentMapper<Synchronize> 	syncMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<EntityState> 	stateMapper;
	
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
		int playerId = newPlayerId();
		
		// Create player entity
		Entity player = EntityFactoryExamples.newServerPlayer(world, playerId);
		player.addToWorld();
		
		int clientInPort = Integer.parseInt(data[1]);
		GameClientImpl client = new GameClientImpl(packet.getAddress(), clientInPort, packet.getPort(), playerId, player);
		send(client, "connected player " + client.getId() + " " + player.getId() );
		
		clientList.add( client );
		Gdx.app.log("New connection: ", packet.getAddress().toString() + ":" + packet.getPort() + " " + data[1] );
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

	@Override
	public GameClient newClient(DatagramPacket packet, int port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void connected(int clientId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void newEntity(EntityUpdate entityUpdate, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateEntity(Entity e, EntityUpdate update) {
		// TODO Auto-generated method stub
		
	}

}
