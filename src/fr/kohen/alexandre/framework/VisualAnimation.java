package fr.kohen.alexandre.framework;

import java.util.Hashtable;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VisualAnimation extends Visual {
	protected Hashtable<String, Animation>	animations;
	protected Animation 					currentAnimation;
	protected String 						currentAnimationName;
	protected float 						stateTime = 0f;
	protected boolean						animated = true;
	
	public void addAnimation(String name, float frameDuration, int mode, TextureRegion... frames) {
		Animation animation = new Animation(frameDuration, frames);
		animation.setPlayMode(mode);
		animations.put(name, animation);
		if ( currentAnimation == null ) {
			currentAnimationName = name;
			currentAnimation = animation;
			sprite = new Sprite(animation.getKeyFrame(stateTime));
		}
	}
	
	public void update(float delta) {
		stateTime += delta;
		sprite.setRegion(currentAnimation.getKeyFrame(stateTime));
	}
	
	public String getCurrentAnim() { return currentAnimationName; }
	
	public void setCurrentAnim(String name) { 
		if ( animations.containsKey(name) ) {
			currentAnimationName = name;
			currentAnimation = animations.get(name);
		}
	}
	
	public Set<String> getAnimations() { return animations.keySet(); }
}
