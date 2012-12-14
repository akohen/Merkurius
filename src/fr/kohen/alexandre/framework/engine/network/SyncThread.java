package fr.kohen.alexandre.framework.engine.network;

import java.io.*;
import java.net.*;

import fr.kohen.alexandre.framework.systems.interfaces.CommunicationSystem;
 
public class SyncThread extends Thread {
	protected DatagramSocket 		socket = null;
	protected CommunicationSystem 	parentSystem;
    
    public SyncThread(CommunicationSystem system) throws IOException {
		super("ServerThread");
		this.parentSystem 	= system;
		socket 				= new DatagramSocket();
    }
    
    public SyncThread(CommunicationSystem system, int port) throws IOException {
		super("ServerThread");
		this.parentSystem 	= system;
		socket 				= new DatagramSocket(port);
    }
    
	public void run() {
		while(true) {
			byte[] buf = new byte[256]; 
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				parentSystem.receive(packet);
			} 
			catch (IOException e) { e.printStackTrace(); }	
		}
	}
	
	public int getLocalPort() { return socket.getLocalPort(); }

}
