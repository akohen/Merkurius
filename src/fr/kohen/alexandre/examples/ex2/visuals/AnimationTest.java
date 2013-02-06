package fr.kohen.alexandre.examples.ex2.visuals;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.VisualAnimation;

public class AnimationTest extends VisualAnimation {
	private static final int        		FRAME_COLS = 6;
    private static final int        		FRAME_ROWS = 16;
	
	public AnimationTest() {
		animations = new Hashtable<String,Animation>();
		
		Texture sheet = new Texture(Gdx.files.internal("data/example1/lord_lard_sheet.png"));

		TextureRegion[][] tmp = TextureRegion.split(
				sheet, 
				sheet.getWidth() / FRAME_COLS, 
				sheet.getHeight() / FRAME_ROWS);

		addAnimation("Test", 0.15f, Animation.LOOP, 
				tmp[0][0], tmp[0][1], tmp[0][2], tmp[0][3], tmp[0][4], tmp[0][5]
			);
	}
	
}
