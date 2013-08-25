package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map.Entry;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.base.C.STATES;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;
import fr.kohen.alexandre.wip.multiplayerRogue.MultiRogueFactory;
import fr.kohen.alexandre.wip.multiplayerRogue.components.*;

public class ClientSystem extends DefaultSyncSystem {
	private ComponentMapper<Player> playerMapper;
	private ComponentMapper<Input> inputMapper;
	private ComponentMapper<Destination> destMapper;
	private ComponentMapper<Synchronize> 	syncMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<EntityState> stateMapper;
	private boolean connected = false;

	public ClientSystem() {
		super(0);
	}
	
	protected void initialize() {
		super.initialize();
		playerMapper 	= ComponentMapper.getFor(Player.class, world);
		inputMapper 	= ComponentMapper.getFor(Input.class, world);
		destMapper 		= ComponentMapper.getFor(Destination.class, world);
	}
	
	@Override
	protected void process(Entity e) {
		EntityState state 		= stateMapper.getSafe(e);
		Transform 	transform 	= transformMapper.getSafe(e);
		Synchronize sync 		= syncMapper.get(e);
		
		if( playerMapper.has(e) && inputMapper.has(e) ) {
			send( "update player " + sync.getId() + " " + inputMapper.get(e).input1 + " " + inputMapper.get(e).input2 );
		}
		
		if( updates.containsKey(sync.getId()) ) {
			EntityUpdate update = updates.get( sync.getId() );
			
			if( state != null ) {
				state.setState( STATES.valueOf(update.getNext()) );	
			}
			
			if( transform != null ) {
				transform.position.x 	= update.getNextFloat();
				transform.position.y 	= update.getNextFloat();
				transform.rotation 		= update.getNextFloat();
			}
			
			if( destMapper.has(e) ) {
				destMapper.get(e).position.x = update.getNextFloat();
				destMapper.get(e).position.y = update.getNextFloat();
			}
			
			updates.remove(sync.getId());
		}
		
	}
	
	protected void end() {
		// If the message has not been removed, the entity doesn't exist yet, so we're creating it here.
		for (Entry<Integer, EntityUpdate> entry : updates.entrySet()) {
			Gdx.app.log("creating: ", entry.getValue().getType() );
			EntityUpdate update = entry.getValue();
			
			if( update.getType().equalsIgnoreCase("player") && connected ) {
				Entity e = MultiRogueFactory.newClientOtherPlayer( world, 1, Float.parseFloat(update.getData()[5]), Float.parseFloat(update.getData()[6]) );
				e.addToWorld();
				Synchronize sync = syncMapper.get(e);
				sync.setId(entry.getKey());
			}
		}
		
		for( DatagramPacket event : events ) {
			String message = new String( event.getData(), 0, event.getLength() );
			String[] data = message.split(" ");
			
			if( data[0].equalsIgnoreCase("connected") ) {
				Gdx.app.log("ClientSystem", "connected");
				MultiRogueFactory.newClientActivePlayer( world, Integer.parseInt(data[2]), Integer.parseInt(data[3]) ).addToWorld();
				this.connected  = true;
			} else if( data[0].equalsIgnoreCase("chat") ) {
				Systems.get(ChatSystem.class, world).newMessage(message.substring(5));
			}
			
		}
		
		
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
	
}
