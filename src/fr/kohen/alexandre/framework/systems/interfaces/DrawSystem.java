package fr.kohen.alexandre.framework.systems.interfaces;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface DrawSystem {

	public void draw(Entity e, SpriteBatch batch);
	public boolean canProcess(Entity e);
}
