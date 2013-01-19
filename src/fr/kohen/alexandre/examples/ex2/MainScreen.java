package fr.kohen.alexandre.examples.ex2;

import fr.kohen.alexandre.framework.GameScreen;
import fr.kohen.alexandre.framework.systems.ControlSystem;
import fr.kohen.alexandre.framework.systems.MovementSystem;
import fr.kohen.alexandre.framework.systems.RenderSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {
		world.setSystem(new RenderSystem());
		world.setSystem(new ControlSystem(1));	
		world.setSystem(new MovementSystem());
	}
	
	@Override
	protected void addEntities() {
		EntityFactoryEx2.addLogo(world, 0, 0, 0);
	}

}
