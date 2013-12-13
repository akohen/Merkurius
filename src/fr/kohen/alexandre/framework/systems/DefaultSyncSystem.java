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
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.network.NetworkUtil;
import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;

/**
 * This system can be extended to provide simple synchronization between clients
 * 
 * In the default configuration:
 * There is a single to which all clients connect. Clients send updates only to the server, and the server sends updates to all clients.
 * Only the server creates synchronized entities and gives them their synchronized ID (which is their id on the server)
 * Entities are synced component by component. To be synced, a component must implement the Syncable interface and be added to SyncSystem.
 * Synced components' data can be sent from the clients to the server, from the server to the clients, or both ways. This behavior is determined per component.
 * Finally, data is only sent if the entity is marked as "active" in the synchronize component. (otherwise, the entity will still be updated)
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
	protected boolean						isServer 	= false;
	protected int							lastPlayerId= 1;
	protected int							portIn 		= 0;
	protected List<GameClient>				clientList 	= new ArrayList<GameClient>();
	protected List<DatagramPacket> 			packets 	= new ArrayList<DatagramPacket>();
	protected List<DatagramPacket> 			events		= new ArrayList<DatagramPacket>();
	protected List<ComponentMapper<?>>		mappers 	= new ArrayList<ComponentMapper<?>>();
	protected List<Integer>					removeList	= new ArrayList<Integer>();
	protected Map<Integer, EntityUpdate>	updates		= new HashMap<Integer, EntityUpdate>();
	protected Map<Class<?>, SyncTypes> 		syncType 	= new HashMap<Class<?>, SyncTypes>();
	protected ComponentMapper<Synchronize> 	syncMapper;
	protected NetworkUtil					network;
	
	
	

	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(float delta, int port, boolean isServer) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
		this.portIn = port;
		this.isServer = isServer;
	}
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(float delta, int port) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
		this.portIn = port;
	}
	
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(float delta, boolean isServer) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
		this.isServer = isServer;
	}
	
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(int delta) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
	}
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem() {
		super(Aspect.getAspectForAll(Synchronize.class), 0);
	}
	
	@SuppressWarnings("unchecked")
	public DefaultSyncSystem(boolean isServer) {
		super(Aspect.getAspectForAll(Synchronize.class), 0);
		this.isServer = isServer;
	}

	
	@Override
	protected void initialize() {	
		syncMapper	= ComponentMapper.getFor(Synchronize.class, world);
        if( portIn > 0 ) {
        	 network	= new NetworkUtil(portIn);
        } else {
        	 network	= new NetworkUtil();
        	 portIn		= network.getPort();
        }
        Gdx.app.log("Sync", "Listening on port " + portIn);
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
		Synchronize	sync = syncMapper.get(e);
		
		if( isServer && syncMapper.get(e).getId() == 0 ) { // Setting the SyncId to the server local id
			syncMapper.get(e).setId( e.getId() );
		}
		
		if( removeList.contains(sync.getId()) ) { // Removing entities
			updates.remove(sync.getId());
			e.deleteFromWorld();
		}
		
		if( updates.containsKey(sync.getId()) ) { // Entity update
			EntityUpdate update = updates.get( sync.getId() );
			try { entityApplyUpdate(e, update); }
			catch (NullPointerException exception) { Gdx.app.log( "SyncSystem", "Update error " ); }						
			updates.remove(sync.getId());
		}
		
		if( sync.isActive() ) { // Sending the entity synchronized data
			String message = entityCreateMessage(e);
			send(message);
		}
		
		
	}

	protected void end() { // Updates about non-existent entities are left, so an entity is created for each remaining update
		for (Entry<Integer, EntityUpdate> entry : updates.entrySet()) { 
			Entity e = newEntity( entry.getValue(), entry.getKey() );
			if( e != null ) {
				e.addToWorld();
				entityApplyUpdate(e, entry.getValue());
			}
		}
	}
	
	
	/**
	 * Adds a syncable component to the system
	 * @param type
	 * @param server
	 */
	public <T extends Component> void addSyncedComponent (Class<T> component, SyncTypes type) {
		if( Syncable.class.isAssignableFrom(component)) {
			mappers.add( ComponentMapper.getFor(component, world) );
			syncType.put(component, type);
		}
	}
	
	protected boolean isComponentSendable(Syncable component) {
		if( isServer ) {
			if( syncType.get(component.getClass()).equals(SyncTypes.ClientToServer) )  return false;
			else return true;
		} else {
			if( syncType.get(component.getClass()).equals(SyncTypes.ServerToClient) )  return false;
			else return true;
		}
	}
	
	protected boolean isComponentReceivable(Syncable component) {
		if( isServer ) {
			if( syncType.get(component.getClass()).equals(SyncTypes.ServerToClient) )  return false;
			else return true;
		} else {
			if( syncType.get(component.getClass()).equals(SyncTypes.ClientToServer) )  return false;
			else return true;
		}
	}
	
	
	/**
	 * Returns true if the sync() method of the component should not be called.
	 * Use to add custom logic.
	 * @param component
	 * @param update
	 * @return true if the component should NOT updated
	 */
	protected boolean isComponentSpecial(Syncable component, EntityUpdate update) {
		if( component instanceof fr.kohen.alexandre.framework.components.Parent ) {
			// Assigning entities the local id of their parent (which must be synchronized!)
			Parent parent = (Parent) component;
			int parentId = update.getNextInteger();			
			if( parent.parentId == 0 && parentId != 0 ) {
				for (int i = 0, s = getActives().size(); s > i; i++) {
					Entity entity = getActives().get(i);
					if( syncMapper.get(entity).getId() == parentId ) {
						parent.parentId = entity.getId();
					}
				}
			}
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Prepares the message to send containing updated data about the entity
	 * @param e
	 * @return the message to send
	 */
	protected String entityCreateMessage(Entity e) {
		Synchronize	sync	= syncMapper.get(e);
		
		StringBuilder message = new StringBuilder();
		message.append("update ").append(sync.getType()).append(" ").append(sync.getId());
		
		for( ComponentMapper<?> mapper : mappers ) {
			Syncable component = (Syncable) mapper.getSafe(e);
			if( component != null ) { // The entity has the current component
				if( isComponentSendable(component) ) { // The entity is the source of authority for this component
					message.append(" ").append(component.getMessage()); 
				}
			}
		}
		return message.toString();
	}
	
	
	/**
	 * Applies the update to the entity
	 * @param e
	 * @param update
	 */
	protected void entityApplyUpdate(Entity e, EntityUpdate update) {		
		for( ComponentMapper<?> mapper : mappers ) {
			Syncable component = (Syncable) mapper.getSafe(e);
			if( component != null && isComponentReceivable(component) ) { 
				if( !isComponentSpecial(component, update) ) {
					component.sync(update);
				}	
			}
		}
	}
	
	
	
	
	// Communication methods
	public void receive(DatagramPacket packet) { 
		String message = new String( packet.getData(), 0, packet.getLength() );
		//Gdx.app.log("Sync", "received " + message);
		String[] data = message.split(" ");
		
		if( data[0].contentEquals("update") ) {
			updates.put( Integer.parseInt(data[2]), new EntityUpdate(data) );
		} else if( data[0].contentEquals("connect") ) {
			addClient( packet,Integer.parseInt(data[1]) );
		} else if( data[0].contentEquals("connected") ) {
			connected( Integer.parseInt(data[2]) );
		} else if( data[0].contentEquals("removed") ) {
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
		network.send(client, message);
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
	/**
	 * Called on clients. Returns the GameClient object identifying the server.
	 * @param inetAddress
	 * @param port
	 * @return
	 */
	protected abstract GameClient addHost(InetAddress inetAddress, int port);
	
	/**
	 * Called on the server each time a new client is connected. Returns the GameClient object identifying the new player.
	 * Can be used to create player entities for example.
	 * @param packet
	 * @param port
	 * @return
	 */
	protected abstract GameClient newClient(DatagramPacket packet, int port);
	
	/**
	 * Called on clients once successfully connected to the server
	 * @param clientId Used by the server to identify the client.
	 */
	protected abstract void connected(int clientId);
	
	/**
	 * Called on clients to create the local version of entities created on the server
	 * @param entityUpdate The data received about the entity
	 * @param syncId the SyncId of the entity
	 * @return
	 */
	protected abstract Entity newEntity(EntityUpdate entityUpdate, int syncId);

	
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
