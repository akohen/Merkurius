package fr.kohen.alexandre.examples.canabalt.systems;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.examples.canabalt.EntityFactoryCana;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;

public class SpawnSystem extends EntityProcessingSystem {

	private ComponentMapper<Transform> 	transformMapper;
	private float lastSpawned = 600;
	
	@SuppressWarnings("unchecked")
	public SpawnSystem() {
		super(Player.class);
	}

	@Override
	public void initialize() {
		this.transformMapper	= new ComponentMapper<Transform>	(Transform.class, world);
	}
	
	
	@Override
	protected void process(Entity e) {
		Vector2f position 	= transformMapper.get(e).getLocation();
		if ( lastSpawned - position.x < 200 )
			spawnBuilding(15);
		
	}
	
	
	private void spawnBuilding(float x) {
		EntityFactoryCana.createBox(world, 1, x+lastSpawned, 410, 200);
		lastSpawned += x + 200;		
	}


}
