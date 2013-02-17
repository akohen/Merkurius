package fr.kohen.alexandre.framework.network;

import java.net.DatagramPacket;
import java.net.InetAddress;

public interface GameClient {
	public InetAddress getAddress();
	public int getPort();
	public boolean checkPacket(DatagramPacket packet);
}
