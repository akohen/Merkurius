package fr.kohen.alexandre.framework.systems.npc;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.Bag;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.Interactable;
import fr.kohen.alexandre.framework.components.Inventory;
import fr.kohen.alexandre.framework.components.Item;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CollisionSystem;
import fr.kohen.alexandre.framework.systems.interfaces.InteractionSystem;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;
import fr.kohen.alexandre.framework.systems.interfaces.ScriptSystem;

public class InteractionSystemBase extends EntityProcessingSystem implements InteractionSystem {
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Parent> 		parentMapper;
	private ComponentMapper<Interactable> 	interactableMapper;
	private ComponentMapper<Inventory> 		inventoryMapper;
	private ComponentMapper<SpatialForm> 	spatialMapper;
	private Bag<Entity> 					interactionMarkers;
	private Bag<Entity> 					collidables;
	private CollisionSystem					collisionSystem;
	private ScriptSystem					scriptSystem;
	private MapSystem						mapSytem;

	@SuppressWarnings("unchecked")
	public InteractionSystemBase() {
		super(Interactable.class);
	}
	
	
	@Override
	public void initialize() {
		transformMapper 	= new ComponentMapper<Transform>	(Transform.class, 		world);
		parentMapper 		= new ComponentMapper<Parent>		(Parent.class, 			world);
		interactableMapper 	= new ComponentMapper<Interactable>	(Interactable.class, 	world);
		inventoryMapper 	= new ComponentMapper<Inventory>	(Inventory.class, 		world);
		spatialMapper 		= new ComponentMapper<SpatialForm>	(SpatialForm.class, 	world);
		collisionSystem 	= Systems.get						(CollisionSystem.class, world);
		scriptSystem 		= Systems.get						(ScriptSystem.class, 	world);
		mapSytem 			= Systems.get						(MapSystem.class, 		world);
		interactionMarkers	= new Bag<Entity>();
		collidables			= new Bag<Entity>();
	}
	

	@Override
	protected void process(Entity e) {
		if( interactableMapper.get(e) == null )
			return;
		
		// If activated: checks for collision with an interaction marker not created by this entity
		if( interactableMapper.get(e).isActivated() ) { 
			for( int i=0; i<interactionMarkers.size();i++) {
				Entity marker = interactionMarkers.get(i);
				if( collisionSystem.checkCollision(e, marker) && e.getId() != parentMapper.get(marker).getParentId() ) {
					Entity parentEntity = world.getEntity(parentMapper.get(marker).getParentId());
					startDialog(parentEntity, e);
					teleport(parentEntity, e);
					pickUp(parentEntity, e);
				}
			}
		}
		
		// Checks for collision with collision entities
		for( int i=0; i<collidables.size();i++) {
			Entity collidable = collidables.get(i);
			if( collisionSystem.checkCollision(e, collidable) && e.getId() != collidable.getId() ) {
				startDialog(e, collidable);
				teleport(e, collidable);
				script(e, collidable);
				pickUp(e, collidable);
			}		
		}
	}
	
	
	@Override
	protected void end() {
		for( int i=0; i<interactionMarkers.size();i++)
			interactionMarkers.get(i).delete();
		interactionMarkers.clear();
	}
	
	
	@Override
	protected void added(Entity e) {
		if( !interactableMapper.get(e).isActivated() )
			collidables.add(e); // Add collision entity to the list
	}
	
	@Override
	protected void removed(Entity e) {
		if( !interactableMapper.get(e).isActivated() )
			collidables.remove(e); // remove collision entity to the list
	}
	
	/**
	 * Adds an interaction marker at the designated location
	 * @param parent
	 * @param location
	 */
	public void addMarker(Entity parent, Vector2f location) {
		interactionMarkers.add( EntityFactory.createInteractionMarker(
				world, parent.getId(),
				parent.getComponent(Transform.class).getMapId(), 
				location.x, 
				location.y));		
	}
	
	
	/**
	 * Teleports e1 according to e2 if possible
	 * @param e1
	 * @param e2
	 * @return true if teleported, false if not
	 */
	private boolean teleport(Entity e1, Entity e2) {
		if( interactableMapper.get(e2).canTeleport() ) {
			transformMapper.get(e1).setLocation( interactableMapper.get(e2).getTeleportLoc() );
			transformMapper.get(e1).setMapId( interactableMapper.get(e2).getTeleportMap()  );
			return true;
		}
		else return false;
	}
	
	/**
	 * Executes e2's script
	 * @param e1
	 * @param e2
	 * @return
	 */
	private boolean script(Entity e1, Entity e2) {
		if( interactableMapper.get(e2).isScript() ) {
			scriptSystem.exec( interactableMapper.get(e2).getScript() );
			return true;
		}
		else return false;
	}
	
	
	/**
	 * Starts a dialog between e1 (initiator) and e2 (respondent) if possible
	 * @param e1
	 * @param e2
	 * @return true if a dialog has been started
	 */
	private boolean startDialog(Entity e1, Entity e2) {
		if( interactableMapper.get(e1).canSpeak() && interactableMapper.get(e2).canSpeak() ) {
			world.getSystemManager().getSystem(DialogSystem.class).startDialog(e1, e2); // Both can talk => Dialog
			
			TiledMap map = mapSytem.getMap(mapSytem.getCurrentMap()).getMap();
			int width = map.getWidth() * map.getTileWidth();
			float ecart = (transformMapper.get(e1).getX() + 72 - transformMapper.get(e2).getX());
			if( (width+ecart)%width < 100 )
				spatialMapper.get(e2).getSpatial().setCurrentAnim("stand_left");
			else
				spatialMapper.get(e2).getSpatial().setCurrentAnim("stand_right");
			return true;
		}
		else return false;			
	}
	
	
	/**
	 * e1 picks e2 if possible
	 * @param e1
	 * @param e2
	 * @return true if e2 picked
	 */
	private boolean pickUp(Entity e1, Entity e2) {
		if( interactableMapper.get(e1).hasInventory() && interactableMapper.get(e2).isItem() ) {
			inventoryMapper.get(e1).addItem( e2.getComponent(Item.class) );
			e2.delete();
			//e2.removeComponent(interactableMapper.get(e2));
			return true;
		}
		else return false;
	}
}