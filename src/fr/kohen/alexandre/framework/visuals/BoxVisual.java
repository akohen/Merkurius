package fr.kohen.alexandre.framework.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.Visual;

public class BoxVisual extends Visual {

	public BoxVisual(int width, int height, Color color) {
		Texture texture = new Texture(Gdx.files.internal("data/pixel.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
		
		sprite = new Sprite(region);
		sprite.setSize(width, height);
		sprite.setOrigin(width/2, height/2);
		sprite.setColor(color);
	}
	
}
