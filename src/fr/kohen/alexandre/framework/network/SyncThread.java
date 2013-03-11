package fr.kohen.alexandre.framework.network;

import java.io.*;
import java.net.*;

import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;
 
public class SyncThread extends Thread {
	protected DatagramSocket 		socket = null;
	protected SyncSystem 	parentSystem;
    
    public SyncThread(SyncSystem system) throws IOException {
		super("ServerThread");
		this.parentSystem 	= system;
		socket 				= new DatagramSocket();
    }
    
    public SyncThread(SyncSystem system, int port) throws IOException {
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
				parentSystem.receiveFromThread(packet);
			} 
			catch (IOException e) { e.printStackTrace(); }	
		}
	}
	
	public int getLocalPort() { return socket.getLocalPort(); }

}
