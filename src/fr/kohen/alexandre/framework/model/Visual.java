package fr.kohen.alexandre.framework.model;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.VisualComponent;

public abstract class Visual {
	protected Hashtable<String, Animation>	animations;
	protected Sprite sprite = null;
	
	public Visual() {
		animations = new Hashtable<String,Animation>();
	}
	
	/*
	public void draw(Transform transform, SpriteBatch batch) {
		sprite.setPosition(
				transform.x - sprite.getOriginX(), 
				transform.y - sprite.getOriginY()
			);
		
		sprite.setRotation(transform.getRotation());
		sprite.setScale(transform.getScale());
		
		sprite.draw(batch);
	}*/
	
	public void draw(VisualComponent visual, Transform transform, SpriteBatch batch) {
		
		setCurrentAnim(visual);
		sprite.setPosition(
				transform.position.x - sprite.getOriginX(), 
				transform.position.y - sprite.getOriginY()
			);
		
		sprite.setRotation(transform.rotation);
		sprite.setScale(transform.scale);
		
		sprite.draw(batch);
	}

	/*
	public void update(float delta) {	
	}
	
	public void updateDraw(float delta, Transform transform, SpriteBatch batch) {
		update(delta);
		draw(transform, batch);
	}*/
	
	public Sprite getSprite() {
		return sprite;
	}
	
	
	//public boolean isAnimated() { return false; }
	
	public void addAnimation(String name, float frameDuration, int mode, TextureRegion... frames) {
		Animation animation = new Animation(frameDuration, frames);
		animation.setPlayMode(mode);
		animations.put(name, animation);
		if( sprite == null ) {
			sprite = new Sprite(animation.getKeyFrame(0));
		}
	}
	
	public void setCurrentAnim(VisualComponent visual) { 
		if ( animations.containsKey(visual.currentAnimationName) ) {
			sprite.setRegion( animations.get(visual.currentAnimationName).getKeyFrame(visual.stateTime) );
		}
	}
	

}
