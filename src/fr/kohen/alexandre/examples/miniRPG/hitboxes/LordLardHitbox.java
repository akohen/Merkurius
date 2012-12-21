package fr.kohen.alexandre.examples.miniRPG.hitboxes;

import org.newdawn.slick.geom.Polygon;

import fr.kohen.alexandre.framework.engine.Spatial;

public class LordLardHitbox extends Spatial {

	public LordLardHitbox() { 
		super();
		size.set(32, 32);
		offset.set(16, 16);
	}

	@Override
	public void initalize() {
		Polygon hitbox = new Polygon();
		hitbox.addPoint(6, 16);		
		hitbox.addPoint(6, 31);
		hitbox.addPoint(26, 31);
		hitbox.addPoint(26, 16);
		hitbox.setClosed(true);
		shape = hitbox;
	}


}
