package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import fr.kohen.alexandre.framework.engine.Spatial;

public class NPCSpatial extends Spatial {

	public NPCSpatial() {
		super();
		color = new Color(Color.yellow);
	}

	@Override
	public void initalize() {
		shape = new Circle(0, 0, 20);
	}


}
