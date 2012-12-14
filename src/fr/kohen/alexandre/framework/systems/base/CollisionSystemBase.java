package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

import fr.kohen.alexandre.framework.components.HitboxForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.interfaces.CollisionSystem;

public class CollisionSystemBase extends EntitySystem implements CollisionSystem {
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<Velocity> 		velocityMapper;
	protected ComponentMapper<HitboxForm> 	hitboxFormMapper;

	@SuppressWarnings("unchecked")
	public CollisionSystemBase() {
		super(HitboxForm.class, Transform.class);
	}

	@Override
	public void initialize() {
		transformMapper 	= new ComponentMapper<Transform>	(Transform.class, world);
		velocityMapper 		= new ComponentMapper<Velocity>		(Velocity.class, world);
		hitboxFormMapper 	= new ComponentMapper<HitboxForm>	(HitboxForm.class, world);
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for( int i=0; entities.size() > i; i++) {
			Entity 		e1 			= entities.get(i);
			Velocity 	velocity 	= velocityMapper.get(e1);
			
			if(velocity != null && 0 < velocity.getSpeed().length() ) {
				
				Vector2f 	speed 		= velocity.getSpeed();

				Vector2f maxSpeedH = speed; // Horizontal priority
				Vector2f maxSpeedV = speed; // Vertical priority
				// Using the two priorities to avoid getting stuck on corners

				for(int j = 0; j < entities.size(); j++) { // Iterates all the solids
					Entity 		e2 = entities.get(j);
					if( !e2.equals(e1) && causeCollision(e1,e2) ) { // Avoid collisions with self						
						maxSpeedH = maxSpeed(e1,e2,maxSpeedH, true);
						maxSpeedV = maxSpeed(e1,e2,maxSpeedV, false);
					} // not self
				} // other entities
				
				//Updating speed
				if( maxSpeedH.lengthSquared() > maxSpeedV.lengthSquared() ) // Choosing the best direction priority
					velocity	.addSpeed( maxSpeedH.x-(int)speed.x, maxSpeedH.y-(int)speed.y );
				else
					velocity	.addSpeed( maxSpeedV.x-(int)speed.x, maxSpeedV.y-(int)speed.y );

			} // has velocity					
		} // e1
		
	}


	
	/**
	 * Checks if there's a collision between two entities
	 * @param e1 the moving object
	 * @param e2 the second object
	 * @param mov movement of the first object
	 * @return true if there is a collision, false otherwise
	 */
	public boolean checkCollision(Entity e1, Entity e2, Vector2f mov) {
		Transform 	t1 = transformMapper.get(e1);
		Transform 	t2 = transformMapper.get(e2);
		if( t1 == null || t2 == null)
			return false;
		
		if( t1.getMapId() == t2.getMapId() ) {
			HitboxForm h1 = hitboxFormMapper.get(e1);
			HitboxForm h2 = hitboxFormMapper.get(e2);
			
			//if( t1.getDistanceTo(t2) > h1.getHitbox().getMaxLen() + h2.getHitbox().getMaxLen() )
			//	return false;
			Shape 		s1 = h1.getShape(t1.getLocation().add(mov), t1.getRotationAsRadians() );
			Shape		s2 = h2.getShape(t2.getLocation(), t2.getRotationAsRadians() );

			return s1.intersects(s2);
		}
		else return false;
	}
	
	
	/**
	 * Checks if there's a collision between two entities
	 * @param e1 the first object
	 * @param e2 the second object
	 * @return true if there is a collision, false otherwise
	 */
	public boolean checkCollision(Entity e1, Entity e2) { return checkCollision(e1, e2, new Vector2f(0,0)); }
	
	
	/**
	 * Checks if the entities can collide
	 * @param e1
	 * @param e2
	 * @return
	 */
	public boolean causeCollision(Entity e1, Entity e2) {
		HitboxForm 	h1 = hitboxFormMapper.get(e1);
		HitboxForm 	h2 = hitboxFormMapper.get(e2);
		if( h1.getType().equals("actor") ) {
			if( h2.getType().equals("solid") )
				return true;
		}
		else if( h1.getType().equals("solid") ) {
			if( h2.getType().equals("actor") || h2.getType().equals("solid") )
				return true;
		}
		return false;
	}
	

	@Override
	public boolean isContained(Entity e1, Entity e2) {
		Transform 	t1 = transformMapper.get(e1);
		Transform 	t2 = transformMapper.get(e2);
		if( t1 == null || t2 == null)
			return false;
		
		if( t1.getMapId() == t2.getMapId() ) {
			HitboxForm h1 = hitboxFormMapper.get(e1);
			HitboxForm h2 = hitboxFormMapper.get(e2);
			
			//if( t1.getDistanceTo(t2) > h1.getHitbox().getMaxLen() + h2.getHitbox().getMaxLen() )
			//	return false;
			Shape 		s1 = h1.getShape(t1.getLocation(), t1.getRotationAsRadians() );
			Shape		s2 = h2.getShape(t2.getLocation(), t2.getRotationAsRadians() );

			return s1.contains(s2);
		}
		else return false;
	}
	
	
	@Override
	protected boolean checkProcessing() { return true; }
	
	
	
	
	/**
	 * Calculate the maximum speed for e1, without colliding with e2. If priority is true, priority is given to horizontal movement
	 * @param e1
	 * @param e2
	 * @param speed
	 * @param priority
	 * @return max speed
	 */
	protected Vector2f maxSpeed(Entity e1, Entity e2, Vector2f speed, boolean priority) {
		int xNew, xMax, yNew, yMax, x, y;
		xMax = (int) speed.x;
		yMax = (int) speed.y;
		int steps = Math.max( Math.abs(xMax), Math.abs(yMax));
		x = 0; y=0; xNew=0; yNew=0;						
			
		for( int k = 0; k <= 2*steps; k+=1) {
			if( xNew != xMax )
				xNew = x + (int) Math.signum(xMax);

			if( yNew != yMax )
				yNew = y + (int) Math.signum(yMax);
			
			if( !checkCollision(e1, e2, new Vector2f(xNew, yNew)) ) {
				x = xNew;
				y = yNew;
			}
			else if(priority) {
				if( !checkCollision(e1, e2, new Vector2f(xNew, y)) ) {
					yMax = y;
					yNew = y;
				}
				else {
					xMax = x;
					xNew = x;
				}
			}
			else {
				if( !checkCollision(e1, e2, new Vector2f(x, yNew)) ) {
					xMax = x;
					xNew = x;
				}
				else {
					yMax = y;
					yNew = y;
				}
			}
				
			
			if( x == xMax && y == yMax ) {
				break;
			}
			
		} // for
		return new Vector2f(x,y);
	}

	
}
