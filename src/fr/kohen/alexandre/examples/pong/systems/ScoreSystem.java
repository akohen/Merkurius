package fr.kohen.alexandre.examples.pong.systems;


import java.util.Random;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.examples.pong.EntityFactoryPong;
import fr.kohen.alexandre.examples.pong.components.Ball;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class ScoreSystem extends EntityProcessingSystem {

	private ComponentMapper<Transform> 	transformMapper;
	private ComponentMapper<Velocity> 	velocityMapper;
	private ComponentMapper<SpatialForm> spatialMapper;
	private Entity textPlayer;
	private Entity textEnemy;
	private int scorePlayer = 0;
	private int scoreEnemy = 0;
	
	@SuppressWarnings("unchecked")
	public ScoreSystem() {
		super(Ball.class);
	}

	@Override
	public void initialize() {
		this.transformMapper	= new ComponentMapper<Transform>	(Transform.class, world);
		this.velocityMapper		= new ComponentMapper<Velocity>		(Velocity.class, world);
		this.spatialMapper		= new ComponentMapper<SpatialForm>	(SpatialForm.class, world);
		this.textPlayer			= EntityFactoryPong.createGuiText(world, 75, 10, "Score: 0");
		this.textEnemy			= EntityFactoryPong.createGuiText(world, 600, 10, "Score: 0");
	}
	
	
	@Override
	protected void process(Entity e) {
		Transform 	transform 	= transformMapper.get(e);
		
		if( transform.getX() < -10 ) {
			scoreEnemy += 1;
			spatialMapper.get(textEnemy).getSpatial().setText("Score: " + scoreEnemy);
			restartGame();	
		}
		if( transform.getX() > 810 ) {		
			scorePlayer += 1;
			spatialMapper.get(textPlayer).getSpatial().setText("Score: " + scorePlayer);
			restartGame();	
		}

	}
	
	
	public void restartGame() {
		
		Entity e = world.getTagManager().getEntity("ball");
		
		Transform 	transform 	= transformMapper.get(e);
		Velocity 	velocity 	= velocityMapper.get(e);
		Random 		random 		= new Random();
		
		transform.setLocation(390, 290);
		velocity.setSpeed( 
				3-6*random.nextInt(2), 
				random.nextInt(7)-3);
	}

}
