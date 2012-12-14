package fr.kohen.alexandre.framework.systems.interfaces;

import java.net.DatagramPacket;
import java.net.UnknownHostException;

import fr.kohen.alexandre.framework.engine.network.GameClient;

/**
 * Basic system for sending and receiving messages
 * @author Alexandre
 */
public interface CommunicationSystem {

	
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
	
	/**
	 * Connect to specified host
	 * @param host
	 * @param port
	 * @throws UnknownHostException 
	 */
	public void connect(String host, int port) throws UnknownHostException;
}
