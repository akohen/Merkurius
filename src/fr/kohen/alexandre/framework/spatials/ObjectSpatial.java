package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import fr.kohen.alexandre.framework.engine.Spatial;

public class ObjectSpatial extends Spatial {

	public ObjectSpatial() {
		super();
		color = new Color(Color.blue);
	}

	@Override
	public void initalize() {
		shape = new Rectangle(0, 0, 20, 20);
	}

}
