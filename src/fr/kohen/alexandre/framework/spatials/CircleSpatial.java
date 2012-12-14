package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import fr.kohen.alexandre.framework.engine.Spatial;

public class CircleSpatial extends Spatial {
	private int radius;

	public CircleSpatial(int radius) {
		super();
		this.radius = radius;
	}

	@Override
	public void initalize() {
		shape = new Circle(0, 0, radius);
		color = new Color(Color.yellow);
	}


}
