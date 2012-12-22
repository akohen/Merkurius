package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Velocity;

public class FrictionSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Velocity> 		velocityMapper;
	protected float xFriction;
	protected float yFriction;

	@SuppressWarnings("unchecked")
	public FrictionSystem(float xFriction, float yFriction) {
		super( Aspect.getAspectForAll(Velocity.class) );
		this.xFriction = xFriction;
		this.yFriction = yFriction;
	}
	
	public FrictionSystem(float friction) {
		this(friction, friction);
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);			
		Vector2f 	speed 		= velocity.getSpeed();
		
		if( Math.abs(speed.x) < xFriction )
			speed.x = 0;
		else
			speed.x -= xFriction * Math.signum(speed.x);
		
		if( Math.abs(speed.y) < yFriction )
			speed.y = 0;
		else
			speed.y -= yFriction * Math.signum(speed.y);
		
		velocity.setSpeed(speed);
		velocity.addRotation( -1 * Math.signum(velocity.getRotation()) );
	}
}	