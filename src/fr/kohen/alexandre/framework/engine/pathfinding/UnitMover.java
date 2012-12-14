package fr.kohen.alexandre.framework.engine.pathfinding;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Mover;

import fr.kohen.alexandre.framework.components.HitboxForm;

public class UnitMover implements Mover {
	
	private int width;
	private int height;
	private Vector2f tileDimension;

	public UnitMover(HitboxForm hitbox, Vector2f tileDimension) {
		this.width = (int) hitbox.getShape().getWidth();
		this.height = (int) hitbox.getShape().getHeight();
		this.tileDimension = tileDimension;
	}
	
	/**
	 * Gets the width of the object
	 * @return int width of the hitbox
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Gets the height of the object
	 * @return int height of the hitbox
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Gets the dimension of the tiles
	 * @return int dimension of the tiles
	 */
	public Vector2f getTileDimension() {
		return tileDimension;
	}
}
