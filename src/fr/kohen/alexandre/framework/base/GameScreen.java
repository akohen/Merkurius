package fr.kohen.alexandre.framework.base;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Screen;

public abstract class GameScreen implements Screen {
	protected World world;

	public GameScreen() {
	}

	public void controllerInit() {
		//TODO add support for returning to a previously initialized screen
		world = new World();
		world.setManager(new TagManager());
		world.setManager(new GroupManager());
		setSystems();
		world.initialize();
		initialize();
	}
	/**
	 * Setting systems for this screen.
	 * Systems will be run in the order they are loaded
	 * Example: renderSystem = world.setSystem(new RenderSystem());
	 */
	protected abstract void setSystems();
	protected abstract void initialize();
	
	@Override
	public void render(float delta) {
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void resize(int width, int height) {	
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {	
	}

	@Override
	public void pause() {	
	}

	@Override
	public void resume() {	
	}

	@Override
	public void dispose() {
	}
	

}
