package fr.kohen.alexandre.games.space.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Destination;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class ApplyDestination extends EntityProcessingSystem {

	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Destination>	destinationMapper;
	
	@SuppressWarnings("unchecked")
	public ApplyDestination() {
		super(Transform.class, Velocity.class, Destination.class);
	}

	@Override
	public void initialize() {
		this.velocityMapper 	= new ComponentMapper<Velocity>(Velocity.class, world);
		this.transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		this.destinationMapper 	= new ComponentMapper<Destination>(Destination.class, world);
	}

	@Override
	protected void process(Entity e) {
		Velocity 	velocity 	= velocityMapper.get(e);
		Transform 	transform 	= transformMapper.get(e);
		Destination destination	= destinationMapper.get(e);
		
		//Vector2f accel = destination.getLocation().copy();
		//accel.add( transform.getLocation().negate() );
		
		
		float rotation = destination.getRotation() - transform.getRotation();
		rotation = (360+rotation) % 360;
		if( rotation >= 180 )
			rotation -= 360;
		
		if( Math.abs(rotation) > 0.5f ) {
			velocity.addRotation( Math.min(5f, Math.abs(rotation + 1)) * Math.signum(rotation) );
		}
		
		if( Math.abs(velocity.getRotation()) > 0.25f )
			velocity.addRotation( - 0.5f * velocity.getRotation() );
		else
			velocity.setRotation(0);
	}
	

}
