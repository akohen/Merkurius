package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;
import fr.kohen.alexandre.wip.multiplayerRogue.*;
import fr.kohen.alexandre.wip.multiplayerRogue.components.*;


public class ServerSystem extends DefaultSyncSystem {

	private ComponentMapper<Synchronize> 	syncMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Destination> destMapper;
	private ComponentMapper<EntityState> stateMapper;

	public ServerSystem(float delta, int port) { super(delta, port); }	
	
	protected void initialize() {
		super.initialize();
		this.destMapper = ComponentMapper.getFor(Destination.class, world);
	}
	
	@Override
	protected void begin() { 
		super.begin();
		
		for( DatagramPacket packet : events ) {
			String message = new String(packet.getData(), 0, packet.getLength());
			String[] data = message.split(" ");
			
			if( data[0].equalsIgnoreCase("connect") ) {
				connectNewPlayer(packet, data);
			} else if( message.startsWith("update player") ) {
				playerUpdate(packet, data);
			} else if( message.startsWith("chat") ) {
				send(message);
			}
		}
	}


	private void connectNewPlayer(DatagramPacket packet, String[] data) {
		// Set player id
		int playerId = newPlayerId();
		
		// Create player entity
		Entity player = MultiRogueFactory.newServerPlayer(world, playerId);
		player.addToWorld();
		
		int clientInPort = Integer.parseInt(data[1]);
		MultiRogueGameClient client = new MultiRogueGameClient(packet.getAddress(), clientInPort, packet.getPort(), playerId, player);
		send(client, "connected player " + client.getId() + " " + player.getId() );
		
		clientList.add( client );
		Gdx.app.log("New connection: ", packet.getAddress().toString() + ":" + packet.getPort() + " " + data[1] );
	}
	
	
	private void playerUpdate(DatagramPacket packet, String[] data) {
		MultiRogueGameClient client = (MultiRogueGameClient) getMessageSender(packet);
		int entityId = Integer.parseInt(data[2]);
		if( client.getShip().getId() == entityId ) {
			updates.put( entityId, new EntityUpdate(data) );
		}
	}

	@Override
	protected void process(Entity e) {
		EntityState	state		= stateMapper		.getSafe(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Synchronize	sync		= syncMapper		.get(e);

		if( updates.containsKey(e.getId()) ) {
			EntityUpdate update = updates.get(e.getId());
			int input1 = update.getNextInteger();
			e.getComponent(Input.class).input1 = input1;
		}
		
		// Creating the message
		String message = "update " + sync.getType() + " " + e.getId();
		
		if( state != null )
			message += " " + state.getState();
		
		if( transform != null )
			message += " " + transform.position.x + " " + transform.position.y + " " + transform.rotation;
		
		if( destMapper.has(e) )
			message += " " + destMapper.get(e).position.x + " " + destMapper.get(e).position.y + " ";

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
