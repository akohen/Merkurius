package fr.kohen.alexandre.examples.multiplayerRogue;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MultiRogueServer {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Merkurius libGDX server";
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 480;
		LwjglApplicationConfiguration.disableAudio = true;
		
		new LwjglApplication(new MultiRogueController(true), cfg);
	}
}
