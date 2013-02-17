package fr.kohen.alexandre.examples.ex2_camera.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.Visual;

public class Logo extends Visual {

	public Logo() {
		Texture texture = new Texture(Gdx.files.internal("data/example1/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		//sprite.setSize(w * 0.9f, h * 0.9f * sprite.getHeight() / sprite.getWidth());
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setPosition(0,0);
		//sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}
	
}
