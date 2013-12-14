package fr.kohen.alexandre.framework.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;

import com.badlogic.gdx.Gdx;
 
public class SyncThread extends Thread {
	protected DatagramSocket 		socket = null;
	protected NetworkUtil 	parentSystem;
    
    public SyncThread(NetworkUtil system) throws IOException {
		super("ServerThread");
		this.parentSystem 	= system;
		socket 				= new DatagramSocket();
    }
    
    public SyncThread(NetworkUtil system, int port) throws IOException {
		super("ServerThread");
		this.parentSystem 	= system;
		socket 				= new DatagramSocket(port);
    }
    
	public void run() {
		UPNP();
		while(true) {
			byte[] buf = new byte[256]; 
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				parentSystem.receiveFromThread(packet);
			} 
			catch (IOException e) { e.printStackTrace(); }	
		}
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
			if (activeGW.getSpecificPortMappingEntry(socket.getLocalPort(),"UDP",portMapping)) {
				Gdx.app.log("Network", "Port "+socket.getLocalPort()+" is already mapped.");
			} else {
				if (activeGW.addPortMapping(socket.getLocalPort(),socket.getLocalPort(),localAddress.getHostAddress(),"TCP","Merkurius Mapping")) {
					Gdx.app.log("Network", "Port "+socket.getLocalPort()+" mapped successfully.");
				}				
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public int getLocalPort() { return socket.getLocalPort(); }

}
