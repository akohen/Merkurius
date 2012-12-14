package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.Bag;

import fr.kohen.alexandre.framework.components.Map;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.MapSystem;
import fr.kohen.alexandre.framework.systems.interfaces.RenderSystem;


public class MapSystemBase extends EntityProcessingSystem implements MapSystem {
	protected RenderSystem 			renderSystem;
	protected ComponentMapper<Map> 	mapMapper;
	protected Bag<Entity> 			maps;
	protected int					currentMap =-1;

	@SuppressWarnings("unchecked")
	public MapSystemBase() {
		super(Map.class);
		this.maps = new Bag<Entity>();
	}

	@Override
	public void initialize() {
		mapMapper 		= new ComponentMapper<Map>(Map.class, world);
		renderSystem	= Systems.get(RenderSystem.class, world);
	}
	

	
	/**
	 * Rendering of the selected layers on the current map
	 * @param layers
	 * @param camera
	 * @param screen
	 */
	public void renderLayers(String layers, Camera camera) {
		renderLayers(currentMap, layers, camera);
	}
	
	
	/**
	 * Rendering of the selected layers on the specified map
	 * @param map
	 * @param type
	 * @param camera
	 * @param screen
	 */
	public void renderLayers(int mapId, String type, Camera camera) {
		Map map = mapMapper.get(maps.get(mapId));
		if( !map.isTiledMap() )
			return;
		
		TiledMap tmap = map.getMap();
		Bag<Integer> layers = getLayers(tmap, type);
		for(int i=0; i<layers.size(); i++)
			renderLayer(mapId, layers.get(i), camera);
	}
	
	
	/**
	 * @param map
	 * @param type
	 * @return Layers ids
	 */
	public Bag<Integer> getLayers(TiledMap tmap, String type) {
		Bag<Integer> layers = new Bag<Integer>();
		
		for( int i=0; i<tmap.getLayerCount(); i++) {
			if( type.equalsIgnoreCase("front") ) {
				if(tmap.getLayerProperty(i, "foreground", "false").equalsIgnoreCase("true") )
					layers.add(i);
			} else if( type.equalsIgnoreCase("back") ) {
				if(tmap.getLayerProperty(i, "foreground", "false").equalsIgnoreCase("false") 
						&& i != tmap.getLayerIndex("background") && i!= tmap.getLayerIndex("collision") )
					layers.add(i);
			}
		}
			
		return layers;
	}
	

	
	/**
	 * Render a single layer
	 * @param layer
	 * @param camera
	 */
	public void renderLayer(int mapId, int layer, Camera camera) {
		/*if( layer == collisionLayer )
			return; // The collision layer should not be rendered
		else if( layer == backgroundLayer )
			tmap.render(0, 0, layer); //No position adjustment for the background layer
			*/
		
		// default rendering, rendering only tiles visible on the screen + 2 tiles on each side
		Map 		map 	= mapMapper.get(maps.get(mapId));
		TiledMap 	tmap 	= map.getMap();
		int startX = (int) (-camera.getPosition().x / tmap.getTileWidth()) -2;
		int startY = (int) (-camera.getPosition().y / tmap.getTileHeight()) -2;
		tmap.render(
				(int) camera.getPosition().x + startX * tmap.getTileWidth(),
				(int) camera.getPosition().y + startY * tmap.getTileHeight(), 
				startX, startY,
				(int) (camera.getScreenSize().x / tmap.getTileWidth())+4, 
				(int) (camera.getScreenSize().y / tmap.getTileHeight())+4,
				layer, false);
	}
	
	

	@Override
	protected void process(Entity e) {
	}
	
	@Override
	protected void added(Entity e) {

		Map 	 map  	= mapMapper.get(e);		
		maps.set(map.getMapId(), e);
		
		if( currentMap == -1 )
			currentMap = map.getMapId();
		
		if( !map.isTiledMap() )
			return;
		
		TiledMap tmap 	= map.getMap();
		
		// Collision layer	
		if( -1 < tmap.getLayerIndex("collision") ) { // if the layer is defined
			for (int x = 0; x < tmap.getWidth(); x++) {
				for (int y = 0; y < tmap.getHeight(); y++) {
					checkTile(x, y, tmap.getLayerIndex("collision"), tmap, map.getMapId());
				}
			}
		}
		
		
		// Objects
		for (int objGroup = 0; objGroup < tmap.getObjectGroupCount(); objGroup++) {
			for (int obj = 0; obj < tmap.getObjectCount(objGroup); obj++) {
				checkObject(objGroup, obj, tmap, map.getMapId());
			}
		}
		
	}

	
	
	/**
	 * Returns the map corresponding to the given id
	 * @param mapId
	 * @return map
	 */
	public Map getMap(int mapId) {
		Entity 	e 	= maps.get(mapId);
		Map 	map = mapMapper.get(e);
		return map;
	}
	
	
	/**
	 * Get current Map ID
	 * @return
	 */
	public int getCurrentMap() {
		return currentMap;
	}

	@Override
	public void enterMap(int mapId) { 
		currentMap = mapId; 
	}
	
	@Override
	public void checkTile(int tileX, int tileY, int layer, TiledMap tmap, int mapId) {	
	}
	

	@Override
	public void checkObject(int objGroup, int obj, TiledMap tmap, int mapId) {
	}

	@Override
	public Vector2f getMapDimensions() { return getMapDimensions(currentMap); }

	@Override
	public Vector2f getMapDimensions(int mapId) {
		return mapMapper.get(maps.get(mapId)).getDimensions();
	}

	
}
