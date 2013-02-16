package fr.kohen.alexandre.examples.ex2.visuals;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.kohen.alexandre.framework.C;
import fr.kohen.alexandre.framework.VisualAnimation;

public class LordLardVisual extends VisualAnimation {
	private static final int        		FRAME_COLS = 6;
    private static final int        		FRAME_ROWS = 16;
	
	public LordLardVisual() {
		animations = new Hashtable<String,Animation>();
		Texture.setEnforcePotImages(false);
		Texture sheet = new Texture(Gdx.files.internal("data/example1/lord_lard_sheet.png"));

		TextureRegion[][] tmp = TextureRegion.split(
				sheet, 
				sheet.getWidth() / FRAME_COLS, 
				sheet.getHeight() / FRAME_ROWS);

		addAnimation(C.WALK_DOWN_RIGHT, 0.15f, Animation.LOOP, tmp[7]);
		
		addAnimation(C.WALK_RIGHT, 0.15f, Animation.LOOP, tmp[6]);
		
		addAnimation(C.WALK_UP_RIGHT, 0.15f, Animation.LOOP, tmp[5]);
		
		addAnimation(C.WALK_UP, 0.15f, Animation.LOOP, tmp[4]);
		
		addAnimation(C.WALK_UP_LEFT, 0.15f, Animation.LOOP, tmp[3]);
		
		addAnimation(C.WALK_LEFT, 0.15f, Animation.LOOP, tmp[2]);
		
		addAnimation(C.WALK_DOWN_LEFT, 0.15f, Animation.LOOP, tmp[1]);
		
		addAnimation(C.WALK_DOWN, 0.15f, Animation.LOOP, 
				tmp[0][0], tmp[0][1], tmp[0][2], tmp[0][3], tmp[0][4], tmp[0][5]
			);

		addAnimation(C.STAND_DOWN_RIGHT, 0.15f, Animation.LOOP, tmp[7][0]);
		addAnimation(C.STAND_RIGHT, 0.15f, Animation.LOOP, tmp[6][0]);
		addAnimation(C.STAND_UP_RIGHT, 0.15f, Animation.LOOP, tmp[5][0]);
		addAnimation(C.STAND_UP, 0.15f, Animation.LOOP, tmp[4][0]);
		addAnimation(C.STAND_UP_LEFT, 0.15f, Animation.LOOP, tmp[3][0]);
		addAnimation(C.STAND_LEFT, 0.15f, Animation.LOOP, tmp[2][0]);
		addAnimation(C.STAND_DOWN_LEFT, 0.15f, Animation.LOOP, tmp[1][0]);
		addAnimation(C.STAND_DOWN, 0.15f, Animation.LOOP, tmp[0][0]);
	}
	
}
