package fr.kohen.alexandre.examples.ex2_camera.visuals;

import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;

public class testVisual1 extends Visual {

	public testVisual1() {
		sprite = new BoxVisual(200, 200, Color.BLACK).getSprite();
	}
	
}
