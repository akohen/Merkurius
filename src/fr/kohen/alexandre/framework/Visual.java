package fr.kohen.alexandre.framework;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.components.Transform;

public abstract class Visual {
	protected Sprite sprite;
	
	public void draw(Transform transform, SpriteBatch batch) {
		sprite.setPosition(
				transform.x - sprite.getOriginX(), 
				transform.y - sprite.getOriginY()
			);
		
		sprite.setRotation(transform.getRotation());
		sprite.setScale(transform.getScale());
		
		sprite.draw(batch);
	}

	public void update(float delta) {	
	}
	
	public Sprite getSprite() {
		return sprite;
	}

}
