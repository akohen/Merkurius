package fr.kohen.alexandre.framework.systems.unused;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFinder;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.IntervalEntityProcessingSystem;

import fr.kohen.alexandre.framework.components.HitboxForm;
import fr.kohen.alexandre.framework.components.Map;
import fr.kohen.alexandre.framework.components.Pathfinding;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.pathfinding.GameMap;
import fr.kohen.alexandre.framework.engine.pathfinding.UnitMover;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;


public class PathfindingSystem extends IntervalEntityProcessingSystem {
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Pathfinding> 	NPCMapper;
	private ComponentMapper<HitboxForm> 	hitboxFormMapper;
	private PathFinder 						localFinder; // Path on local map
	//private PathFinder 						worldFinder; // Path between maps
	private MapSystemBase 						mapSystem;
	

	@SuppressWarnings("unchecked")
	public PathfindingSystem(int delta) {
		super(delta, Transform.class, Pathfinding.class);
	}

	@Override
	public void initialize() {
		mapSystem 			= world.getSystemManager().getSystem(MapSystemBase.class);
		transformMapper 	= new ComponentMapper<Transform>(Transform.class, world);
		NPCMapper 			= new ComponentMapper<Pathfinding>(Pathfinding.class, world);
		hitboxFormMapper 	= new ComponentMapper<HitboxForm>(HitboxForm.class, world);
		//this.worldFinder = new AStarPathFinder(new WorldMap(), 100, false);
		
	}
	
	public void loadMap(Map localMap) {
		this.localFinder = new AStarPathFinder(new GameMap(localMap), 100, true);
	}

	@Override
	protected void process(Entity e) {
		Pathfinding npc = this.NPCMapper.get(e);
		//TODO: check path availabilty or recalculate often?
		if( npc.getUpdate() ) { // if the path needs to be updated
			if( npc.getDestinationMap() == transformMapper.get(e).getMapId() ) { // Is the NPC on his destination map?
				Vector2f destination = npc.getDestination();
				Vector2f location 	 = transformMapper.get(e).getLocation();
				Vector2f tileDim 	 = mapSystem.getMap( transformMapper.get(e).getMapId() ).getTileDimension();
				if( location.distance(destination) > 0 )  { // Destination not reached		
					npc.setPath(localFinder.findPath( 
							new UnitMover( hitboxFormMapper.get(e), tileDim ), // Creating the Mover object
							getTileX(location.x, tileDim), getTileY(location.y, tileDim), // Start position
							getTileX(destination.x, tileDim), getTileY(destination.y, tileDim)) ); // Destination
				}
			}
			else { // The NPC has to change map to reach its destination
				//world movement
			}
		}			
	}
	
	/**
	 * Returns the tile corresponding to a pixel position
	 * @param x
	 * @param tileDim
	 * @return
	 */
	private int getTileX(float x, Vector2f tileDim) {
		return (int) ((int) (x - (x%tileDim.x))/tileDim.x);		
	}
	
	/**
	 * Returns the tile corresponding to a pixel position
	 * @param y
	 * @param tileDim
	 * @return
	 */
	private int getTileY(float y, Vector2f tileDim) {
		return (int) ((int) (y - (y%tileDim.y))/tileDim.y);			
	}
}	