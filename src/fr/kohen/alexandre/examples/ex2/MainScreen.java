package fr.kohen.alexandre.examples.ex2;

import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.GameScreen;
import fr.kohen.alexandre.framework.systems.CameraSystem;
import fr.kohen.alexandre.framework.systems.ControlSystem;
import fr.kohen.alexandre.framework.systems.MovementSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem(new CameraSystem());
		world.setSystem(new RenderSystem());
		world.setSystem(new ControlSystem(1));	
		world.setSystem(new MovementSystem());
	}
	
	@Override
	protected void addEntities() {
		EntityFactoryEx2.addLogo(world, 1, 0, 0);
		EntityFactoryEx2.addCamera(world, 1, 0, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, "test");
	}

}
