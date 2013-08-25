package fr.kohen.alexandre.wip.multiplayerRogue;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.network.GameClient;


public class MultiRogueGameClient implements GameClient {
	protected InetAddress 	address;
	protected int			port;
	protected int			portOut;
	protected int			id;
	protected Entity		ship;
	

	public MultiRogueGameClient(InetAddress address, int port, int portOut, int id, Entity ship) {
		this.address 	= address;
		this.port 		= port;
		this.portOut 	= portOut;
		this.id 		= id;
		this.ship 		= ship;
	}
	
	public MultiRogueGameClient(InetAddress address, int port) {
		this.address 	= address;
		this.port 		= port;
	}

	public InetAddress getAddress() { return this.address; }
	public int getPort() { return this.port; }
	public int getId() { return this.id; }
	public Entity getShip() { return this.ship; }
	public Entity getEntity() { return this.ship; }

	@Override
	public boolean checkPacket(DatagramPacket packet) {
		if( packet.getAddress().getHostAddress().equalsIgnoreCase(address.getHostAddress()) && packet.getPort() == portOut )
			return true;
		else
			return false;
	}
}
