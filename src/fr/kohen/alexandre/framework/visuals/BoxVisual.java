package fr.kohen.alexandre.framework.visuals;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.Visual;

public class BoxVisual extends Visual {

	public BoxVisual(int width, int height, Color color) {

		Pixmap pixmap = new Pixmap( 1, 1, Format.RGBA8888 );
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 1, 1);
		TextureRegion pixmaptex = new TextureRegion(new Texture(pixmap));
		pixmap.dispose();
		
		sprite = new Sprite(pixmaptex);
		sprite.setSize(width, height);
		sprite.setOrigin(width/2, height/2);
	}
	
}
