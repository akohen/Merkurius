package fr.kohen.alexandre.framework.systems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import fr.kohen.alexandre.framework.network.SyncThread;
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
	protected DatagramSocket 				socket;	
	protected List<DatagramPacket> 			packets;
	protected int							portIn = 0;
	protected SyncThread 					syncThread;
	protected List<DatagramPacket> 			events;
	protected Map<Integer, EntityUpdate>	updates;
	protected List<Integer>					removeList;
	protected int							lastPlayerId = 1;

	protected ComponentMapper<Synchronize> 	syncMapper;
	
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
		packets			= Collections.synchronizedList(new ArrayList<DatagramPacket>());

        syncMapper  	= ComponentMapper.getFor(Synchronize.class, world);
		
		try { 
			if( portIn > 0 )
				this.syncThread = new SyncThread(this, portIn);
			else
				this.syncThread = new SyncThread(this);
		} catch (IOException e) { e.printStackTrace(); }
		this.syncThread.start();
		this.portIn = this.syncThread.getLocalPort();
		
		try { this.socket = new DatagramSocket(); } 
		catch (SocketException e) { e.printStackTrace(); }
	}
	
	protected void begin() {
		events.clear();
		updates.clear();
		removeList.clear();
		
		synchronized(packets) {
			Iterator<DatagramPacket> i = packets.iterator(); // Must be in synchronized block
			while (i.hasNext()) {
				receive(i.next());
			}
			packets.clear();
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
	

	public void receiveFromThread(DatagramPacket packet) {
		packets.add(packet);
	}
	
	
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
	
	/**
	 * Send a message to all connected clients
	 */
	public void send(String message) {
		for( GameClient client : clientList ) {
			send(client, message);
		}
	}
	
	/**
	 * Send a message to a single client
	 */
	public void send(GameClient client, String message) {
		byte[] buf = message.getBytes();
		//Gdx.app.log("SyncSystem", "Sending message '" + message + "' to " + client.getAddress() + ":" + client.getPort());
		DatagramPacket packet = new DatagramPacket( buf, buf.length, client.getAddress(), client.getPort() );
		try { socket.send(packet); } catch (IOException e1) { e1.printStackTrace(); }		
	}
	
	/**
	 * 
	 * @param packet
	 * @param port
	 */
	private void addClient(DatagramPacket packet, int port) {
		GameClient client = newClient(packet, port);
		clientList.add( client );
		send( client, "connected player " + client.getId() );
	}
	
	/**
	 * Connect to a server listening on the specified port
	 * @param address
	 * @param port
	 * @throws UnknownHostException
	 */
	public void connect(String address, int port) throws UnknownHostException {
		GameClient host = addHost(InetAddress.getByName(address), port);
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
	
	
	/**
	 * Does some thing in old style.
	 *
	 * @deprecated use {@link connect(String address, int port)} instead.  
	 */
	@Deprecated
	public void connect(GameClient host) throws UnknownHostException {
		clientList.add(host);
		send("CONNECT " + portIn);
		Gdx.app.log("SyncSystem", "connecting");
	}

}
