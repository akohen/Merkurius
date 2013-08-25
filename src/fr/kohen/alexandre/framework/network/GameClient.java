package fr.kohen.alexandre.framework.network;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.Entity;

public interface GameClient {
	public InetAddress getAddress();
	public int getPort();
	public int getId();
	public boolean checkPacket(DatagramPacket packet);
	public Entity getEntity();
}
