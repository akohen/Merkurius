package fr.kohen.alexandre.examples.pong.systems;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.systems.base.CollisionSystemBase;

public class CollisionSystemPong extends CollisionSystemBase {

	protected int ballId;
	
	protected void begin() {
		ballId = world.getTagManager().getEntity("ball").getId();
	}
	
	public boolean checkCollision(Entity e1, Entity e2, Vector2f mov) {
		boolean collision = super.checkCollision(e1, e2, mov);
		
		if( collision && e1.getId() == ballId ) {
			Vector2f speed = velocityMapper.get(e1).getSpeed();
			if( hitboxFormMapper.get(e2).getType().equalsIgnoreCase("actor") ) {
				Vector2f speedPlayer = velocityMapper.get(e2).getSpeed();
				velocityMapper.get(e1).setSpeed(new Vector2f(-speed.x, speed.y + speedPlayer.y/4 ));				
			}
			else
				velocityMapper.get(e1).setSpeed(new Vector2f(speed.x, -speed.y));
		}
		
		return collision;
	}
}
