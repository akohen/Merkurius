package fr.kohen.alexandre.framework.systems.interfaces;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.components.Map;

public interface MapSystem {
	
	/**
	 * Checks the designated tile and creates entities if necessary
	 * @param tileX
	 * @param tileY
	 * @param layer
	 * @param tmap
	 * @param mapId
	 */
	public void checkTile(int tileX, int tileY, int layer, TiledMap tmap, int mapId);
	
	
	/**
	 * Check an object and create entities if necessary
	 * @param objGroup
	 * @param obj
	 * @param tmap
	 * @param mapId
	 */
	public void checkObject(int objGroup, int obj, TiledMap tmap, int mapId);
	
	
	/**
	 * Returns the map corresponding to the given id
	 * @param mapId
	 * @return map
	 */
	public Map getMap(int mapId);
	
	
	/**
	 * Get current Map ID
	 * @return
	 */
	public int getCurrentMap();
	
	
	/**
	 * Enters the selected map
	 * @param mapId
	 */
	public void enterMap(int mapId);
	
	
	/**
	 * Rendering of the selected layers on the current map
	 * @param layers
	 * @param camera
	 */
	public void renderLayers(String layers, Entity camera);
	
	/**
	 * @return Dimensions of the current map
	 */
	public Vector2f getMapDimensions();
	
	/**
	 * @param mapId
	 * @return Dimensions of the specified map
	 */
	public Vector2f getMapDimensions(int mapId);
}
