package fr.kohen.alexandre.tests.spatials;

import org.newdawn.slick.geom.Polygon;

import fr.kohen.alexandre.framework.engine.Spatial;

public class Arrow extends Spatial {
	private String direction;

	public Arrow(String direction) {
		super();
		this.direction = direction;
	}

	@Override
	public void initalize() {
		Polygon ship = new Polygon();
		if( direction.equalsIgnoreCase("up")) {
			ship.addPoint(-20, 16);
			ship.addPoint(20, 16);
			ship.addPoint(0, -16);
		}
		else if( direction.equalsIgnoreCase("down")) {
			ship.addPoint(-20, -16);
			ship.addPoint(20, -16);
			ship.addPoint(0, 16);
		}
		else if( direction.equalsIgnoreCase("left")) {
			ship.addPoint(16, -20);
			ship.addPoint(16, 20);
			ship.addPoint(-16, 0);
		}
		else {
			ship.addPoint(-16, -20);
			ship.addPoint(-16, 20);
			ship.addPoint(16, 0);
		}
		ship.closed();
		shape = ship;
	}

}
