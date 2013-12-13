package fr.kohen.alexandre.framework.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;

import com.badlogic.gdx.Gdx;

public class NetworkUtil {

	protected int							portIn = 0;
	protected SyncThread 					syncThread;
	protected DatagramSocket 				socket;
	protected List<DatagramPacket> 			packets;
	
	
	/**
	 * Will open two sockets to send and receive game data on any available port
	 */
	public NetworkUtil() {
		try { 
			this.syncThread = new SyncThread(this);
		} catch (IOException e) { e.printStackTrace(); }
		init();
	}
	
	
	/**
	 * Will open two sockets to send and receive game data with the specified listening port
	 * @param portIn incoming port
	 */
	public NetworkUtil(int portIn) {
		try { this.syncThread = new SyncThread(this, portIn);
		} catch (IOException e) { e.printStackTrace(); }
		init();
	}
	
	
	private void init() {
		packets			= Collections.synchronizedList(new ArrayList<DatagramPacket>());
		
		this.syncThread.start();
		this.portIn = this.syncThread.getLocalPort();
		
		try { this.socket = new DatagramSocket(); }
		catch (SocketException e) { e.printStackTrace(); }
		UPNP();
	}
	
	
	private void UPNP() {
		GatewayDiscover gatewayDiscover = new GatewayDiscover();
		try {
			gatewayDiscover.discover();
			GatewayDevice activeGW = gatewayDiscover.getValidGateway();
			if (null == activeGW) {
				Gdx.app.log("Network", "No active Gateway, aborting");
				return;
			}
			InetAddress localAddress = activeGW.getLocalAddress();
			String externalIPAddress = activeGW.getExternalIPAddress(); 
			Gdx.app.log("Network", "Internal address " + localAddress.getHostAddress() + " - External address " + externalIPAddress);
			
			PortMappingEntry portMapping = new PortMappingEntry();
			if (activeGW.getSpecificPortMappingEntry(this.portIn,"UDP",portMapping)) {
				Gdx.app.log("Network", "Port "+this.portIn+" is already mapped.");
			} else {
				if (activeGW.addPortMapping(this.portIn,this.portIn,localAddress.getHostAddress(),"TCP","Merkurius Mapping")) {
					Gdx.app.log("Network", "Port "+this.portIn+" mapped successfully.");
				}				
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	
	/**
	 * Send the message to the client
	 * @param client
	 * @param message
	 */
	public void send(GameClient client, String message) {
		byte[] buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket( buf, buf.length, client.getAddress(), client.getPort() );
		try { socket.send(packet); } catch (IOException e1) { e1.printStackTrace(); }	
	}
	
	
	public void receiveFromThread(DatagramPacket packet) {
		packets.add(packet);
	}
	
	
	/**
	 * @return A list of all packets received since the last call to this method
	 */
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
	
	
	/**
	 * @return The listening port
	 */
	public int getPort() {
		return portIn;
	}
}
