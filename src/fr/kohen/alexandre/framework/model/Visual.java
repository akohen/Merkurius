package fr.kohen.alexandre.framework.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.kohen.alexandre.framework.components.Transform;

public abstract class Visual {
	protected Sprite sprite = null;
	
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
	
	public void updateDraw(float delta, Transform transform, SpriteBatch batch) {
		update(delta);
		draw(transform, batch);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isAnimated() { return false; }

}
