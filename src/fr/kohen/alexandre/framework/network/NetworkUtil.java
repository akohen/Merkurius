package fr.kohen.alexandre.framework.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NetworkUtil {

	protected int							portIn = 0;
	protected SyncThread 					syncThread;
	protected DatagramSocket 				socket;
	protected List<DatagramPacket> 			packets;
	
	
	public NetworkUtil() {
		packets			= Collections.synchronizedList(new ArrayList<DatagramPacket>());
		
		try { 
			this.syncThread = new SyncThread(this);
		} catch (IOException e) { e.printStackTrace(); }
		this.syncThread.start();
		this.portIn = this.syncThread.getLocalPort();
		
		try { this.socket = new DatagramSocket(); } 
		catch (SocketException e) { e.printStackTrace(); }
	}
	
	
	public NetworkUtil(int portIn) {
		packets			= Collections.synchronizedList(new ArrayList<DatagramPacket>());
		
		try { 
			this.syncThread = new SyncThread(this, portIn);
		} catch (IOException e) { e.printStackTrace(); }
		this.syncThread.start();
		this.portIn = this.syncThread.getLocalPort();
		
		try { this.socket = new DatagramSocket(); } 
		catch (SocketException e) { e.printStackTrace(); }
	}
	
	public void send(GameClient client, String message) {
		byte[] buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket( buf, buf.length, client.getAddress(), client.getPort() );
		try { socket.send(packet); } catch (IOException e1) { e1.printStackTrace(); }	
	}
	
	
	public void receiveFromThread(DatagramPacket packet) {
		packets.add(packet);
	}
	
	public List<DatagramPacket> getPackets() {
		List<DatagramPacket> list = new ArrayList<DatagramPacket>();
		synchronized(packets) {
			Iterator<DatagramPacket> i = packets.iterator(); // Must be in synchronized block
			while (i.hasNext()) {
				list.add(i.next());
			}
			packets.clear();
		}
		return list;
	}
	
	public int getPort() {
		return portIn;
	}
}
