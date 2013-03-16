package fr.kohen.alexandre.examples.multiplayerRogue.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import fr.kohen.alexandre.framework.components.Player;

public class SightSystem extends EntityProcessingSystem implements RayCastCallback {

	//private PhysicsSystem physicsSystem;

	@SuppressWarnings("unchecked")
	public SightSystem() {
		super( Aspect.getAspectForAll(Player.class) );
	}
	
	protected void initialize() {
		//physicsSystem = Systems.get(PhysicsSystem.class, world);
	} 
	
	public boolean isInSight(Entity e1, Entity e2) {
		//physicsSystem
		return false;
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		return 0;
	}

	@Override
	protected void process(Entity e) {
		
	}

}
