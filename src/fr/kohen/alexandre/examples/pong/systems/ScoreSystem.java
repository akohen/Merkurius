package fr.kohen.alexandre.examples.pong.systems;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.examples.pong.EntityFactoryPong;
import fr.kohen.alexandre.examples.pong.components.Ball;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class ScoreSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<SpatialForm> 	spatialMapper;
	@Mapper ComponentMapper<Velocity> 		velocityMapper;
	private Entity textPlayer;
	private Entity textEnemy;
	private int scorePlayer = 0;
	private int scoreEnemy = 0;
	
	@SuppressWarnings("unchecked")
	public ScoreSystem() {
		super( Aspect.getAspectForAll(Ball.class) );
	}

	@Override
	public void initialize() {
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
		
		Entity e = world.getManager(TagManager.class).getEntity("ball");
		
		Transform 	transform 	= transformMapper.get(e);
		Velocity 	velocity 	= velocityMapper.get(e);
		Random 		random 		= new Random();
		
		transform.setLocation(390, 290);
		velocity.setSpeed( 
				3-6*random.nextInt(2), 
				random.nextInt(7)-3);
	}

}
