package fr.kohen.alexandre.examples.network;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.Entity;

import fr.kohen.alexandre.examples._common.EntityFactoryExamples;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class NetworkExampleSyncSystem extends DefaultSyncSystem {

	public NetworkExampleSyncSystem(float f, int i, boolean b) {
		super(f,i,b);
	}

	public NetworkExampleSyncSystem() {
		super();
	}

	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		return new NetworkExampleGameClient(inetAddress, port);
	}

	@Override
	protected GameClient newClient(DatagramPacket packet, int port) {
		int playerId = newPlayerId();
		return new NetworkExampleGameClient(packet.getAddress(), port, packet.getPort(), playerId, null);
	}

	@Override
	protected void connected(int clientId) {
	}

	@Override
	protected Entity newEntity(EntityUpdate entityUpdate, int id) {
		if( entityUpdate.getType().equalsIgnoreCase("player") ) {
			EntityFactoryExamples.newPlayer(world, 1, 0, 0).addComponent( new Synchronize("player", false, id) ).addToWorld();
		}
		return null;
	}

}
