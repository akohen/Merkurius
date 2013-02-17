package fr.kohen.alexandre.framework.systems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.network.SyncThread;
import fr.kohen.alexandre.framework.systems.interfaces.ISyncSystem;

public class SyncSystem extends IntervalEntityProcessingSystem implements ISyncSystem {

	protected List<GameClient>		clientList;
	protected DatagramSocket 		socket;	
	protected List<DatagramPacket> 	packets;
	protected int					portIn = 0;
	protected int					portOut = 0;
	protected SyncThread 			syncThread;
	
	
	@SuppressWarnings("unchecked")
	public SyncSystem(float delta, int port) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
		this.portIn = port;
	}
	
	
	@SuppressWarnings("unchecked")
	public SyncSystem(int delta) {
		super(Aspect.getAspectForAll(Synchronize.class), delta );
	}

	
	@Override
	public void initialize() {
		this.clientList 		= new ArrayList<GameClient>();
		this.packets			= Collections.synchronizedList(new ArrayList<DatagramPacket>());
		
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
		synchronized(packets) {
			Iterator<DatagramPacket> i = packets.iterator(); // Must be in synchronized block
			while (i.hasNext()) {
				receive(i.next());
			}
			packets.clear();
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
		byte[] buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket( buf, buf.length, client.getAddress(), client.getPort() );
		try { socket.send(packet); } catch (IOException e1) { e1.printStackTrace(); }		
	}

	
	@Override
	public void receiveFromThread(DatagramPacket packet) {
		packets.add(packet);
	}

	
	@Override
	public void connect(GameClient host) throws UnknownHostException {
		clientList.add(host);
		send("CONNECT " + portIn);
		Gdx.app.log("SyncSystem", "connecting");
	}


	@Override
	public void receive(DatagramPacket packet) { 
		
	}

	

}
