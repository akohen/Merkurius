package fr.kohen.alexandre.examples.pong.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.examples.pong.components.Enemy;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class EnemySystem extends EntityProcessingSystem {

	private ComponentMapper<Transform> 	transformMapper;
	private ComponentMapper<Velocity> 	velocityMapper;
	private Entity ball;
	private float accel;
	
	@SuppressWarnings("unchecked")
	public EnemySystem(float accel) {
		super(Enemy.class);
		this.accel = accel;
	}

	@Override
	public void initialize() {
		this.transformMapper	= new ComponentMapper<Transform>	(Transform.class, world);
		this.velocityMapper		= new ComponentMapper<Velocity>		(Velocity.class, world);
	}
	
	@Override
	protected void process(Entity e) {
		// Checking that we know the ball entity
		if( ball == null )
			ball = world.getTagManager().getEntity("ball");
		
		// Fetching components
		Velocity 	velocityEnemy	= velocityMapper.get(e);
		Transform 	transformEnemy 	= transformMapper.get(e);
		Transform 	transformBall 	= transformMapper.get(ball);
		
		// Calculating the move distance
		float move = transformBall.getY() - transformEnemy.getY() - 40;
		move = Math.min(move, accel);
		move = Math.max(move, -accel);
		
		// Adding speed
		velocityEnemy.addSpeed(0,move);
	}


}
