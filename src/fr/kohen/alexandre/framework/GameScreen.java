package fr.kohen.alexandre.framework;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

public abstract class GameScreen implements Screen {
	protected World world;
	private FPSLogger fps;

	public GameScreen() {
	}

	public void controllerInit() {
		world = new World();
		world.setManager(new TagManager());
		world.setManager(new GroupManager());
		setSystems();
		world.initialize();
		initialize();
		fps = new FPSLogger();
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
		fps.log();
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
