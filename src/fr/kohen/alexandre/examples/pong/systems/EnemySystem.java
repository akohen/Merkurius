package fr.kohen.alexandre.examples.pong.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.examples.pong.components.Enemy;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class EnemySystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<Velocity> 		velocityMapper;
	private Entity ball;
	private float accel;
	
	@SuppressWarnings("unchecked")
	public EnemySystem(float accel) {
		super( Aspect.getAspectForAll(Enemy.class) );
		this.accel = accel;
	}

	@Override
	public void initialize() {
	}
	
	@Override
	protected void process(Entity e) {
		// Checking that we know the ball entity
		if( ball == null )
			ball = world.getManager(TagManager.class).getEntity("ball");
		
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
