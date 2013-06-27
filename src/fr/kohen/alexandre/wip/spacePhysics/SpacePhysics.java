package fr.kohen.alexandre.wip.spacePhysics;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SpacePhysics {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Merkurius space physics test";
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 480;
		
		new LwjglApplication(new SpacePhysicsController(), cfg);
	}
}
