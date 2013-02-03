package fr.kohen.alexandre.examples.ex2.visuals;

import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.Visual;
import fr.kohen.alexandre.framework.visuals.BoxVisual;

public class testVisual1 extends Visual {

	public testVisual1() {
		sprite = new BoxVisual(200, 200, Color.BLACK).getSprite();
	}
	
}
