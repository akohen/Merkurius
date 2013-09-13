package fr.kohen.alexandre.examples.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;

public class Test {
	public static void main(String[] args) {
		ServerSocket server = Gdx.net.newServerSocket(Protocol.TCP, 9999, null);
	}
}
