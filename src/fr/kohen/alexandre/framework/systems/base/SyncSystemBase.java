package fr.kohen.alexandre.framework.systems.base;

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
import java.util.concurrent.ConcurrentHashMap;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.engine.network.GameClient;
import fr.kohen.alexandre.framework.engine.network.SyncThread;
import fr.kohen.alexandre.framework.systems.interfaces.CommunicationSystem;
import fr.kohen.alexandre.games.space.GameClientImpl;

public class SyncSystemBase extends IntervalEntityProcessingSystem implements CommunicationSystem {

	protected List<GameClient>		clientList;
	protected byte[] 				buf;
	protected DatagramPacket 		packet;
	protected DatagramSocket 		socket;	
	protected ConcurrentHashMap<Integer,String>	messagesReceived;
	protected Map<Integer, String> 	messages;
	protected List<String> 			eventsReceived;
	protected List<String> 			eventsRemoved;
	protected List<String> 			events;
	protected int					portIn = 0;
	protected int					portOut = 0;
	protected SyncThread 			syncThread;
	
	
	@SuppressWarnings("unchecked")
	public SyncSystemBase(int delta, int port) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
		this.portIn = port;
	}
	
	@SuppressWarnings("unchecked")
	public SyncSystemBase(int delta) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
	}

	
	@Override
	public void initialize() {
		this.clientList 		= new ArrayList<GameClient>();
		this.messagesReceived 	= new ConcurrentHashMap<Integer,String>();
		this.messages			= new HashMap<Integer,String>();
		this.eventsReceived		= Collections.synchronizedList(new ArrayList<String>());
		this.eventsRemoved		= new ArrayList<String>();
		this.events				= new ArrayList<String>();
		
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
		for (Entry<Integer, String> entry : messagesReceived.entrySet()) {
			messages.put( entry.getKey(), entry.getValue() );
			messagesReceived.remove( entry.getKey(), entry.getValue() );
		}

		synchronized(eventsReceived) {
			Iterator<String> i = eventsReceived.iterator(); // Must be in synchronized block
			while (i.hasNext())
				events.add(i.next());
			eventsReceived.clear();
		}
	}
	
	
	@Override
	protected void process(Entity e) { }
	
	
	protected void removed(Entity e) { }
	
	
	public void send(String message) {
		for( GameClient client : clientList) {
			send(client, message);
		}	
	}
	
	
	@Override
	public void send(GameClient client, String message) {
		buf = message.getBytes();
		packet = new DatagramPacket( buf, buf.length, client.getAddress(), client.getPort() );
		try { socket.send(packet); } catch (IOException e1) { e1.printStackTrace(); }		
	}

	
	@Override
	public void receive(DatagramPacket packet) {
		String message = new String(packet.getData(), 0, packet.getLength());
		String[] data = message.split(" ");
		
		try{
			messagesReceived.put(Integer.parseInt(data[0]), message);
		}
		catch(NumberFormatException e) {
			eventsReceived.add(message);
		}
		
	}

	
	@Override
	public void connect(String host, int port) throws UnknownHostException {
		clientList.add(new GameClientImpl(InetAddress.getByName(host), port));
		send("CONNECT " + portIn);
	}

	

}
