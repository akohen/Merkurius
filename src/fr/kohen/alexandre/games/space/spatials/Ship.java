package fr.kohen.alexandre.games.space.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;

import fr.kohen.alexandre.framework.engine.Spatial;

public class Ship extends Spatial {
	public Ship() {
		super();
		//colorBase = new Color(Color.blue);
		color = new Color(255,255,255,250);
	}

	@Override
	public void initalize() {
		Polygon ship = new Polygon();
		ship.addPoint(20, 0);
		ship.addPoint(-20, -10);
		ship.addPoint(-20, 10);
		shape = ship;
	}

}
