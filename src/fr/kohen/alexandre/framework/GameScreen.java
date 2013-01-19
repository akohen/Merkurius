package fr.kohen.alexandre.framework;

import com.artemis.World;
import com.badlogic.gdx.Screen;


public abstract class GameScreen implements Screen {
	protected World world;

	public GameScreen() {
		world = new World();
		setSystems();
		world.initialize();
		
		addEntities();
	}

	protected abstract void setSystems();
	protected abstract void addEntities();
	
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
