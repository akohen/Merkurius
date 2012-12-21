package fr.kohen.alexandre.framework.spatials;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Spatial;

public class BoxSpatial extends Spatial {

	/**
	 * Creates a solid box
	 * @param width
	 * @param height
	 */
	public BoxSpatial(int width, int height) {
		super();
		size.set(width, height);
		offset.set(width/2, height/2);
	}


	@Override
	public void initalize() {
		shape = new Rectangle(0,0,size.x,size.y);
	}
	
	public void render(Graphics g, Transform transform, Vector2f shift, float rotation, Color color) {		
		g.setColor(color);
		g.setAntiAlias(false);
		
		g.rotate( transform.getX()+shift.x, transform.getY()+shift.y,transform.getRotation() );
		shape.setCenterX(transform.getX()+shift.x);
		shape.setCenterY(transform.getY()+shift.y);
		
		g.fill(shape);
		
		g.setAntiAlias(true);
		g.fill(shape);
		
		g.resetTransform();
	}
	
	public Shape getShape(Vector2f loc, float angle) {
		Shape shapePositioned = shape.transform( org.newdawn.slick.geom.Transform.createRotateTransform(angle) );
		shapePositioned.setCenterX(loc.x);
		shapePositioned.setCenterY(loc.y);
		
		return shapePositioned;
	}

}
