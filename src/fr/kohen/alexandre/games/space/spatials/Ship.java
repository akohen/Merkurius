package fr.kohen.alexandre.games.space.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Camera;
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

	@Override
	public void render(Graphics g, Transform transform, Camera camera, Color color) {
		g.setColor(color);
		g.setAntiAlias(false);
		// shift.x = -playerTransform.getLocation().x + container.getWidth()/2;
		g.rotate( camera.getScreenSize().x/2, camera.getScreenSize().y/2, camera.getRotation() );
		
		g.rotate( transform.getX()+camera.getPosition().x, transform.getY()+camera.getPosition().y,transform.getRotation() );
		shape.setLocation(transform.getX()+camera.getPosition().x, transform.getY()+camera.getPosition().y);
		g.draw(shape);
		g.resetTransform();
	}

}
