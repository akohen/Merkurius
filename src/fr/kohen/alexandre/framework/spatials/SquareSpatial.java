package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Spatial;

public class SquareSpatial extends Spatial {
	private Rectangle rect;
	private float width;
	private float height;

	public SquareSpatial(int i, int j) {
		super();
		this.width = i;
		this.height = j;
	}

	@Override
	public void initalize() {
		rect = new Rectangle(0, 0, this.width, this.height);
		color = Color.green;
	}
	
	public void render(Graphics g, Transform transform, Vector2f shift, float rotation, Color color) {
		g.setColor(color);
		g.setAntiAlias(false);
		rect.setLocation(transform.getX()+shift.x, transform.getY()+shift.y);
		g.fill(rect);
	}

}
