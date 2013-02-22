package fr.kohen.alexandre.examples.ex3_network;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Ex3Client {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Merkurius libGDX client";
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 480;
		
		new LwjglApplication(new GameControllerEx3(false), cfg);
	}
}