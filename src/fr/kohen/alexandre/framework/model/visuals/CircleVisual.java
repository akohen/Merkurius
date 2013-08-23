package fr.kohen.alexandre.framework.model.visuals;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.model.Visual;

public class CircleVisual extends Visual {

	public CircleVisual(int radius, Color color) {
		Pixmap pixmap = new Pixmap(2*radius+2, 2*radius+2, Format.RGBA8888 );
		pixmap.setColor(color);
		pixmap.fillCircle(radius, radius, radius);
		TextureRegion pixmaptex = new TextureRegion(new Texture(pixmap));
		pixmap.dispose();
		
		sprite = new Sprite(pixmaptex);		
	}
	
}
