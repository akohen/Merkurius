package fr.kohen.alexandre.framework;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.components.Transform;

public abstract class Visual {
	protected Sprite sprite;
	
	public void draw(Transform transform, SpriteBatch batch) {
		sprite.setPosition(
				transform.getX() - sprite.getOriginX(), 
				transform.getY() - sprite.getOriginY()
			);
		sprite.draw(batch);
	}

	public void update(float delta) {	
	}

}
