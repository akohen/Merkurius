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

public class CollisionSystemFast extends EntitySystem implements CollisionSystem {
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<Velocity> 		velocityMapper;
	protected ComponentMapper<HitboxForm> 	hitboxFormMapper;

	@SuppressWarnings("unchecked")
	public CollisionSystemFast() {
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
			
			if(velocity != null && ( 0 < velocity.getSpeed().length() || Math.abs(velocity.getRotation()) > 0 ) ) {
				
				Vector2f 	speed 		= velocity.getSpeed();
				float		rotation	= velocity.getRotation();

				for(int j = 0; j < entities.size(); j++) { // Iterates all the solids
					Entity 		e2 = entities.get(j);
					if( !e2.equals(e1) && causeCollision(e1,e2) ) { // Avoid collisions with self						
						if( checkCollision(e1, e2, speed, rotation) ) {
							speed 		= new Vector2f(0,0);
							rotation	= 0;
						}
					} // not self
				} // other entities
				
				velocity	.setSpeed( speed );
				velocity	.setRotation(rotation);

			} // has velocity					
		} // e1
		
	}


	
	/**
	 * Checks if there's a collision between two entities
	 * @param e1 the moving object
	 * @param e2 the second object
	 * @param mov movement of the first object
	 * @param rotation rotation of the first object
	 * @return true if there is a collision, false otherwise
	 */
	public boolean checkCollision(Entity e1, Entity e2, Vector2f mov, float rotation) {
		Transform 	t1 = transformMapper.get(e1);
		Transform 	t2 = transformMapper.get(e2);
		if( t1 == null || t2 == null)
			return false;
		
		if( t1.getMapId() == t2.getMapId() ) {
			HitboxForm h1 = hitboxFormMapper.get(e1);
			HitboxForm h2 = hitboxFormMapper.get(e2);
			
			//if( t1.getDistanceTo(t2) > h1.getHitbox().getMaxLen() + h2.getHitbox().getMaxLen() )
			//	return false;
			Shape 		s1 = h1.getShape(t1.getLocation().add(mov), (float) (t1.getRotationAsRadians() + rotation * Math.PI / 180) );
			Shape		s2 = h2.getShape(t2.getLocation(), t2.getRotationAsRadians() );

			return s1.intersects(s2);
		}
		else return false;
	}
	
	
	/**
	 * Checks if there's a collision between two entities
	 * @param e1 the first object
	 * @param e2 the second object
	 * @param mov movement of the first object
	 * @return true if there is a collision, false otherwise
	 */
	public boolean checkCollision(Entity e1, Entity e2, Vector2f mov) { return checkCollision(e1, e2, mov, 0); }
	
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
	protected boolean checkProcessing() { return true; }

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

	
}
