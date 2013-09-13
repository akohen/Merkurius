package fr.kohen.alexandre.wip.net;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class NetTest {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Merkurius libGDX net test";
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 480;
		
		new LwjglApplication(new NetController(), cfg);
	}
}
