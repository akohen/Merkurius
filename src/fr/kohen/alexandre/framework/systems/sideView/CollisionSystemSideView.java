package fr.kohen.alexandre.framework.systems.sideView;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

import fr.kohen.alexandre.framework.components.HitboxForm;
import fr.kohen.alexandre.framework.components.Jump;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.systems.interfaces.CollisionSystem;

public class CollisionSystemSideView extends EntitySystem implements CollisionSystem {
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<HitboxForm> 	hitboxFormMapper;
	@SuppressWarnings("unused")
	private ComponentMapper<SpatialForm> 	spatialMapper;
	private ComponentMapper<Jump> 			jumpMapper;

	@SuppressWarnings("unchecked")
	public CollisionSystemSideView() {
		super(Transform.class, HitboxForm.class);
	}

	@Override
	public void initialize() {
		transformMapper 	= new ComponentMapper<Transform>	(Transform.class, world);
		velocityMapper 		= new ComponentMapper<Velocity>		(Velocity.class, world);
		hitboxFormMapper 	= new ComponentMapper<HitboxForm>	(HitboxForm.class, world);
		spatialMapper 		= new ComponentMapper<SpatialForm>	(SpatialForm.class, world);
		jumpMapper 			= new ComponentMapper<Jump>			(Jump.class, world);
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for( int i=0; entities.size() > i; i++) {
			Entity 		e1 			= entities.get(i);
			Velocity 	velocity 	= velocityMapper.get(e1);
			
			if(velocity != null) {

				if( 0 < velocity.getSpeed().length() ) {
					Transform 	transform 	= transformMapper	.get(e1);	
					Jump 		jump 		= jumpMapper		.get(e1);	
					Vector2f 	speed 		= velocity			.getSpeed();
					//Vector2f	orgLoc		= transform			.getLocation();
					
					
					if( jump != null ) // If the entity can jump
						jump.setCanJump(false); // Can't jump until it touches the ground
					
					// Updating speed									
					speed.x -= 0.2f*Math.signum(speed.x);
					/*speed.x = Math.min(speed.x, velocity.getMaxSpeed().x);
					speed.x = Math.max(speed.x, -velocity.getMaxSpeed().x);
					speed.y = Math.min(speed.y, velocity.getMaxSpeed().y);
					speed.y = Math.max(speed.y, -velocity.getMaxSpeed().y);*/
					if( Math.abs(speed.x) < 0.1f )
						speed.x = 0;
					if( Math.abs(speed.y) > 0.2f )
						speed.y -= 0.2f;
					else
						speed.y = 0;
					int x = (int) speed.x; 
					int y = (int) speed.y;
					
					for(int j = 0; entities.size() > j; j++) { // Iterates all the solids
						Entity 		e2 = entities.get(j);
						if( !e2.equals(e1) ) { // Avoid collisions with self
							
							//TODO generic collision handling
							if( causeCollision(e1,e2) ) { // Movement
								
								// Jumping
								if( jump != null ) { // If the entity can jump, check if it's on the ground
									if( speed.y >= 0 && checkCollision(e1, e2, new Vector2f(0, 1)) ) {
										jump.setCanJump(true);
										speed.y = 0;
									}
								}
								
								// Generic movements
								int xNew, xMax, yNew, yMax;
								xMax = x;
								yMax = y;
								int steps = Math.max( Math.abs(xMax), Math.abs(yMax));
								x = 0; y=0; xNew=0; yNew=0;								
									
								for( int k = 0; k <= 10*steps; k+=1) {
									if( xNew != xMax )
										xNew = x + (int) Math.signum(xMax);

									if( yNew != yMax )
										yNew = y + (int) Math.signum(yMax);
									
									if( !checkCollision(e1, e2, new Vector2f(xNew, yNew)) ) {
										x = xNew;
										y = yNew;
									}
									else {
										if( !checkCollision(e1, e2, new Vector2f(xNew, y)) ) {
											yMax = y;
											yNew = y;
										}
										else {
											xMax = x;
											xNew = x;
										}
									}
									if( x == xMax && y == yMax )
										break;
								}
							}
							
						} // not self
					} // other entities
					
					
					//Updating speed
					velocity	.addSpeed( x-(int)speed.x, y-(int)speed.y );
					
					//Updating position
					transform	.addVector(new Vector2f(x,y));
					
					// Updating animations
					//TODO remove this from the collision system
					
					/*
					boolean jumping = false;
					if( jump != null )
						jumping = !jump.canJump();	
					spatialMapper	.get(e1).getSpatial().setCurrentAnim(transform.getLocation().sub(orgLoc), jumping);
					 */
					
				} // has speed > 0
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
			Shape 		s1 = h1.getShape(t1.getLocation().add(mov), t1.getRotationAsRadians());
			Shape		s2 = h2.getShape(t2.getLocation(), t2.getRotationAsRadians());

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

	
}
