package fr.kohen.alexandre.framework.engine;

import java.util.Hashtable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Transform;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public abstract class Spatial {
	
	protected World 		world;
	protected Shape			shape;
	protected SpriteSheet 	sheet;
	protected Hashtable<String, Animation> animations = new Hashtable<String, Animation>();
	protected String 		currentAnim;
	protected Image 		currentImage;
	protected int 			duration 	= 200;
	protected Color			color		= Color.white;
	protected Vector2f		size		= new Vector2f(0,0);
	protected Vector2f		offset 		= new Vector2f(0,0);
	
	//TODO remove
	protected Entity 		owner;
	protected Transform 	transform;
	
	public Spatial() {  }
	public Spatial(Entity owner) { 
		this.owner 		= owner;
		this.transform 	= new ComponentMapper<Transform>(Transform.class, world).get(owner);
	}

	public abstract void initalize();
	
	public void render() { }

	//public void render(Graphics g, Vector2f loc, Color color) { }

	public void render(Graphics g) 								{ render(g, transform, new Camera(), color); }
	
	public void render(Graphics g, Color color) 				{ render(g, transform, new Camera(), color); }
	
	public void render(Graphics g, Camera camera) 				{ render(g, transform, camera, color); }
	
	public void render(Graphics g, Camera camera, Color color) 	{ render(g, transform, camera, color); }
	
	public void render(Graphics g, Transform transform, Camera camera, Color color) {
		g.setColor(color);
		g.setAntiAlias(false);

		g.rotate( camera.getScreenSize().x/2, camera.getScreenSize().y/2, camera.getRotation() );
		//g.rotate( transform.getX()+camera.getPosition().x, transform.getY()+camera.getPosition().y, transform.getRotation() );
		
		if( currentAnim != null )
			animations.get(currentAnim).draw(
					transform.getX() + camera.getPosition().x, 
					transform.getY() + camera.getPosition().y, 32, 32, color);
		else if( currentImage != null) {
			currentImage.draw( transform.getX()+camera.getPosition().x, transform.getY()+camera.getPosition().y , 1.0f, color);
		}
		else {
			shape.setLocation( transform.getX()+camera.getPosition().x, transform.getY()+camera.getPosition().y );
			g.draw(shape);
		}

		g.resetTransform();
	}
	
	
	public void render(Graphics g, Transform transform) { render(g,transform,color); }
	
	
	/**
	 * 
	 * @param g graphics context
	 * @param transform Transform component of the entity to draw
	 */
	public void render(Graphics g, Transform transform, Color color) {
		g.setColor(color);
		g.setAntiAlias(false);

		Vector2f center =  transform.getLocation().sub(offset);
		
		// Rotating the graphics context to apply object rotation
		g.rotate( center.x, center.y, transform.getRotation() );
		
		// Drawing only one type
		if( currentAnim != null ) {
			animations.get(currentAnim).draw( center.x, center.y, size.x, size.y, color );
		}
		else if( currentImage != null) {
			currentImage.draw( center.x, center.y , 1.0f, color);
		}
		else {
			shape.setLocation( center );
			g.draw(shape);
		}
		
		// Cancelling the object rotation
		g.rotate( center.x, center.y, -transform.getRotation() );
	}

	
	/**
	 * Return the offset of this spatial
	 * @return
	 */
	public Vector2f getOffset() { return offset; }
	
	public Vector2f getSize() { return size; }
	
	public void update(int delta) {	 }
	
	public void setText(String text) { }
	
	public void setColor(Color color) { this.color = color; }
	
	public void setTransform(Transform transform) { this.transform = transform; }
	
	public void setLineWidth(int width) { }
	
	public boolean hasAnim() { if( currentAnim != null ) return true; else return false; }
	
	public Shape getShape() { return shape; }
	
	public Shape getShape(Vector2f loc, float angle) {
		Shape shapePositioned = shape.transform( org.newdawn.slick.geom.Transform.createRotateTransform(angle) );
		shapePositioned.setLocation(loc);
		
		return shapePositioned;
	}


	/**
	 * Add animation to entity, first animation added is current animation
	 * 
	 * @param name
	 * @param loop
	 * @param row
	 * @param frames
	 */
	public void addAnimation(String name, boolean loop, int row, int... frames) {
		Animation anim = new Animation(false);
		anim.setLooping(loop);
		for (int i = 0; i < frames.length; i++) {
			anim.addFrame(sheet.getSprite(frames[i], row), duration);
		}
		if (animations.size() == 0) {
			currentAnim = name;
		}
		animations.put(name, anim);
	}
	
	protected void updateAnimation(int delta) {
		if (animations != null) {
			if (currentAnim != null) {
				Animation anim = animations.get(currentAnim);
				if (anim != null) {
					anim.update(delta);
				}
			}
		}
	}
	
	/**
	 * Set an image as graphic
	 * @param image
	 */
	public void setGraphic(Image image) {
		this.currentImage = image;
		//this.width = image.getWidth();
		//this.height = image.getHeight();
	}

	/**
	 * Set a sprite sheet as graphic
	 * @param sheet
	 */
	public void setGraphic(SpriteSheet sheet) {
		this.sheet = sheet;
		//this.width = sheet.getSprite(0, 0).getWidth();
		//this.height = sheet.getSprite(0, 0).getHeight();
	}
	
	public String getCurrentAnim() {
		return currentAnim;
	}

	public void setCurrentAnim(String currentAnim) {
		this.currentAnim = currentAnim;
	}

}
