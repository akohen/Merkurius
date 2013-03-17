package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.systems.interfaces.PhysicsSystem;

public class MultiRogueVisibilitySystem extends IntervalEntityProcessingSystem implements RayCastCallback, VisibilitySystem {

	private PhysicsSystem physicsSystem;
	private List<Entity> players;
	private ComponentMapper<Transform> transformMapper;
	private final int MIN_DISTANCE = 30;
	private List<Entity> contacts;
	private Map<Entity, List<Entity>> visibilityMap;

	@SuppressWarnings("unchecked")
	public MultiRogueVisibilitySystem() {
		super( Aspect.getAspectForAll(Player.class), 1f );
		this.players = new ArrayList<Entity>();
		this.contacts= new ArrayList<Entity>(); 
		visibilityMap = new HashMap<Entity, List<Entity>>();
	}
	
	protected void initialize() {
		physicsSystem = Systems.get(PhysicsSystem.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
	} 
	
	@Override
	protected void process(Entity e) {
		visibilityMap.get(e).clear();
		for (Entity player : players ) {
			checkSight(e,player);
		}
		
	}
	
	public void checkSight(Entity e1, Entity e2) {
		if( e1 == e2 )
			return;
		
		boolean lineOfSight = true;
		if( transformMapper.get(e1).getPosition2().dst(transformMapper.get(e2).getPosition2()) > MIN_DISTANCE) {
			contacts.clear();
			physicsSystem.raycast(
					transformMapper.get(e1).mapId, 
					this, 
					transformMapper.get(e1).getPosition2(),
					transformMapper.get(e2).getPosition2() 
				);
			
			for(Entity contact : contacts) {
				if( contact != e2) {
					lineOfSight = false;
					//TODO careful: on client LOS can be broken by GUI or mouse!
				}
			}
		}
		
		if( lineOfSight ) {
			visibilityMap.get(e1).add(e2);
		}
	}
	

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		contacts.add( (Entity) fixture.getBody().getUserData() );
		return fraction;
	}

	public boolean hasLineOfSight(Entity e1, Entity e2) {
		if( visibilityMap.get(e1).contains(e2) ){
			return true;
		} else {
			return false;
		}
	}
	
	
	/* Uncomment for visibility report each turn => Replace by graphical display in debug mode
	 * protected void end() {
		for (Entity player : players) {
			for (Entity player2 : players) {
				if( player != player2 ) Gdx.app.log( "Visibility", player + " " + player2 + " " + hasLineOfSight(player, player2) );
			}
		}
	}*/
	
	protected void inserted(Entity e) {
		this.players.add(e);
		this.visibilityMap.put( e, new ArrayList<Entity>() );
	}
	
	protected void removed(Entity e) {
		this.players.remove(e);
		this.visibilityMap.remove(e);
	}

}
