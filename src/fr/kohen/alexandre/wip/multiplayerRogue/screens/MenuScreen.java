package fr.kohen.alexandre.wip.multiplayerRogue.screens;

import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.*;
import fr.kohen.alexandre.wip.multiplayerRogue.MultiRogueFactory;

public class MenuScreen extends GameScreen {
	
	@Override
	protected void setSystems() {
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultScreenSystem(this) );	
		world.setSystem( new DefaultMouseSystem() );
		world.setSystem( new DefaultActionSystem(MultiRogueFactory.actions) );
		world.setSystem( new DefaultVisualSystem(MultiRogueFactory.visuals) );
		world.setSystem( new DefaultTextSystem() );	
	}
	
	@Override
	protected void initialize() {
		MultiRogueFactory.newServerButton(world, 1, 0, 100).addToWorld();
		MultiRogueFactory.newText(world, 1, 40, 105, "Start as Server").addToWorld();
		

		MultiRogueFactory.newClientButton(world, 1, 0, -100).addToWorld();
		MultiRogueFactory.newText(world, 1, 40, -95, "Start as Client").addToWorld();

		MultiRogueFactory.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "camera").addToWorld();
	}

}
