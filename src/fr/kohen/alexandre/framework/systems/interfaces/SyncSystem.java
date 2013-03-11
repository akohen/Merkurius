package fr.kohen.alexandre.framework.systems.interfaces;

import java.net.DatagramPacket;
import java.net.UnknownHostException;

import fr.kohen.alexandre.framework.network.GameClient;

/**
 * Basic system for sending and receiving messages
 * @author Alexandre
 */
public interface SyncSystem {

	
	/**
	 * Sends a message to every connected client
	 * @param message
	 */
	public void send(String message);
	
	/**
	 * Sends a message to the specified client
	 * @param client
	 * @param message
	 */
	public void send(GameClient client, String message);
	
	/**
	 * Process a received message
	 * @param packet
	 */
	public void receive(DatagramPacket packet);
	public void receiveFromThread(DatagramPacket packet);
	/**
	 * Connect to specified host
	 * @param host
	 * @throws UnknownHostException 
	 */
	public void connect(GameClient host) throws UnknownHostException;

	
}
