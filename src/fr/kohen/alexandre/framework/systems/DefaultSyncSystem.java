package fr.kohen.alexandre.framework.systems;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.network.NetworkUtil;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;

/**
 * This system can be extended to provide simple synchronization between clients
 * 
 * The default message format is:
 * update entity_synchronization_type entity_synchronization_id components_fields...
 * These messages can be accessed with the updates map.
 * 
 * Other messages can be sent with the following format:
 * keyword data...
 * 
 * All messages can be accessed with the events list.
 * 
 * events and updates variables are cleared at the beginning of each loop.
 * 
 * The entity_synchronization_id should be the id of the entity on the server (or the authoritative client for this entity)
 * 
 * @author Alexandre
 *
 */
public abstract class DefaultSyncSystem extends IntervalEntityProcessingSystem implements SyncSystem {
	protected List<GameClient>				clientList;
	protected List<DatagramPacket> 			packets;
	protected List<DatagramPacket> 			events;
	protected Map<Integer, EntityUpdate>	updates;
	protected List<Integer>					removeList;
	protected int							lastPlayerId = 1;
	protected ComponentMapper<Synchronize> 	syncMapper;
	protected int							portIn = 0;
	protected NetworkUtil					network;
	
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(float delta, int port) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
		this.portIn = port;
	}
	
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(int delta) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
	}

	
	@Override
	protected void initialize() {
		updates			= new HashMap<Integer, EntityUpdate>();
		events			= new ArrayList<DatagramPacket>();
		removeList		= new ArrayList<Integer>();
		clientList 		= new ArrayList<GameClient>();
		packets			= new ArrayList<DatagramPacket>();

        syncMapper  	= ComponentMapper.getFor(Synchronize.class, world);
        if( portIn > 0 ) {
        	 network	= new NetworkUtil(portIn);
        } else {
        	 network	= new NetworkUtil();
        	 portIn		= network.getPort();
        }
	}
	
	protected void begin() {
		events.clear();
		updates.clear();
		removeList.clear();
		
		packets = network.getPackets();
		for(DatagramPacket packet  : packets) {
			receive(packet);
		}
	}
	
	@Override
	protected void process(Entity e) {
		Synchronize	sync		= syncMapper		.get(e);
		if( removeList.contains(sync.getId()) ) {
			updates.remove(sync.getId());
			e.deleteFromWorld();
		}
		if( updates.containsKey(sync.getId()) ) {
			EntityUpdate update = updates.get( sync.getId() );
			try { updateEntity(e, update); }
			catch (NullPointerException exception) { Gdx.app.log( "SyncSystem", "Update error " ); }						
			updates.remove(sync.getId());
		}
	}

	protected void end() {
		for (Entry<Integer, EntityUpdate> entry : updates.entrySet()) {
			newEntity( entry.getValue(), entry.getKey() );
		}
	}
	
	
	// Communication methods
	
	@Override
	public void receive(DatagramPacket packet) { 
		String message = new String( packet.getData(), 0, packet.getLength() );
		//Gdx.app.log("Sync", "received " + message);
		String[] data = message.split(" ");
		
		if( data[0].contentEquals("update") ) {
			updates.put( Integer.parseInt(data[2]), new EntityUpdate(data) );
		} else if( data[0].equalsIgnoreCase("connect") ) {
			addClient( packet,Integer.parseInt(data[1]) );
		} else if( data[0].equalsIgnoreCase("connected") ) {
			connected( Integer.parseInt(data[2]) );
		} else if( data[0].equalsIgnoreCase("removed") ) {
			removeList.add( Integer.parseInt(data[1]) );
		} else {
			events.add( packet );
		}
		
	}
	
	public void send(String message) {
		for( GameClient client : clientList ) {
			send(client, message);
		}
	}
	
	public void send(GameClient client, String message) {
		//Gdx.app.log("Sync", "sending " + message);
		network.send(client, message);
	}
	
	
	private void addClient(DatagramPacket packet, int port) {
		GameClient client = newClient(packet, port);
		clientList.add( client );
		send( client, "connected player " + client.getId() );
	}
	
	
	public void connect(String address, int port) throws UnknownHostException {
		GameClient host = addHost(InetAddress.getByName("127.0.0.1"), port);
		clientList.add( host );
		send(host, "connect " + this.portIn );
	}
	
	protected GameClient getMessageSender(DatagramPacket packet) {
		for( GameClient client : clientList ) {
			if( client.checkPacket(packet) ) {
				return client;
			}
		}
		return null;
	}
	
	protected int newPlayerId() { return lastPlayerId++; }
	protected int getLastPlayerId() { return lastPlayerId; }
	protected abstract GameClient addHost(InetAddress inetAddress, int port);
	protected abstract GameClient newClient(DatagramPacket packet, int port);
	protected abstract void connected(int clientId);
	protected abstract void newEntity(EntityUpdate entityUpdate, int id);
	protected abstract void updateEntity(Entity e, EntityUpdate update);

	
	public class EntityUpdate {
		private String[] data;
		private int pointer = 3;

		public EntityUpdate(String[] data) { this.data = data; }
		public EntityUpdate(String[] data, int start) { this.data = data; this.pointer = start; }
		
		public void reset() { this.pointer = 3; }
		public boolean hasNext() { return pointer < data.length; }
		public String getType() { return data[1]; }
		public String getNext() { if(hasNext()) return data[pointer++]; else return null;}
		public Float getNextFloat() { return Float.valueOf(getNext()); }
		public Integer getNextInteger() { return Integer.valueOf(getNext()); }
		public Boolean getNextBoolean() { return Boolean.valueOf(getNext()); }
		public String[] getData() { return data; }
	}
	
	
	// Deprecated
	
	public void connect(GameClient host) throws UnknownHostException {
		clientList.add(host);
		send("CONNECT " + portIn);
		Gdx.app.log("SyncSystem", "connecting");
	}

}
