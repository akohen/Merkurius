package fr.kohen.alexandre.framework.systems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
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
public class DefaultSyncSystem extends IntervalEntityProcessingSystem implements SyncSystem {
	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected ComponentMapper<Transform> 	transformMapper;
	protected ComponentMapper<Synchronize> 	syncMapper;
	protected List<GameClient>				clientList;
	protected DatagramSocket 				socket;	
	protected List<DatagramPacket> 			packets;
	protected int							portIn = 0;
	protected int							portOut = 0;
	protected SyncThread 					syncThread;
	protected List<DatagramPacket> 			events;
	protected Map<Integer, EntityUpdate>	updates;
	
	
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
		transformMapper = ComponentMapper.getFor(Transform.class, world);
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		stateMapper 	= ComponentMapper.getFor(EntityState.class, world);
		syncMapper 		= ComponentMapper.getFor(Synchronize.class, world);
		
		updates			= new HashMap<Integer, EntityUpdate>();
		events			= new ArrayList<DatagramPacket>();
		clientList 		= new ArrayList<GameClient>();
		packets			= Collections.synchronizedList(new ArrayList<DatagramPacket>());
		
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
	
	@Override
	public void receiveFromThread(DatagramPacket packet) {
		packets.add(packet);
	}

	protected void begin() {
		events.clear();
		updates.clear();
		
		synchronized(packets) {
			Iterator<DatagramPacket> i = packets.iterator(); // Must be in synchronized block
			while (i.hasNext()) {
				receive(i.next());
			}
			packets.clear();
		}
	}
	
	@Override
	public void receive(DatagramPacket packet) { 
		String message = new String( packet.getData(), 0, packet.getLength() );
		String[] data = message.split(" ");
		
		if( data[0].contentEquals("update") ) {
			updates.put( Integer.parseInt(data[2]), new EntityUpdate(data) );
		} else {
			events.add( packet );
		}
		
	}
	
	@Override
	public void connect(GameClient host) throws UnknownHostException {
		clientList.add(host);
		send("CONNECT " + portIn);
		Gdx.app.log("SyncSystem", "connecting");
	}
	
	public void send(String message) {
		for( GameClient client : clientList ) {
			send(client, message);
		}	
	}
	
	
	@Override
	public void send(GameClient client, String message) {
		byte[] buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket( buf, buf.length, client.getAddress(), client.getPort() );
		try { socket.send(packet); } catch (IOException e1) { e1.printStackTrace(); }		
	}
	
	@Override
	protected void process(Entity e) { }
	
	protected class EntityUpdate {
		private String[] data;
		private int pointer = 3;

		public EntityUpdate(String[] data) { this.data = data; }
		public EntityUpdate(String[] data, int start) { this.data = data; this.pointer = start; }
		
		public void reset() { this.pointer = 3; }
		public boolean hasNext() { return pointer < data.length; }
		public String getType() { return data[1]; }
		public String getNext() { return data[pointer++]; }
		public Float getNextFloat() { return Float.valueOf(data[pointer++]); }
		public Integer getNextInteger() { return Integer.valueOf(data[pointer++]); }
		public String[] getData() { return data; }
	}
	

}
