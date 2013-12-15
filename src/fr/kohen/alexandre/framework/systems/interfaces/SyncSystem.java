package fr.kohen.alexandre.framework.systems.interfaces;

import java.net.DatagramPacket;
import java.net.UnknownHostException;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.GameClient;

/**
 * Basic system for sending and receiving messages
 * @author Alexandre
 */
public interface SyncSystem {
	public enum SyncTypes {ServerToClient, ClientToServer, Both};
	
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
	 * @throws UnknownHostException 
	 */
	public void connect(String address, int port) throws UnknownHostException;
	
	
	public <T extends Component> void addSyncedComponent (Class<T> component, SyncTypes type);

	
}
