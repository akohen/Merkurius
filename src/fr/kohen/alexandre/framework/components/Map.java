package fr.kohen.alexandre.framework.components;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.artemis.Component;

import fr.kohen.alexandre.framework.engine.resources.ResourceManager;

/**
 * Maps
 * @author Alexandre
 *
 */
public class Map extends Component {

	private TiledMap tmap;
	private int mapId;
	private int width;
	private int height;

	
	public Map(int mapId, int width, int height) {
		this.mapId = mapId;
		this.width = width;
		this.height = height;
	}
	
	
	/**
	 * Map component
	 * @param mapId Id of this map
	 * @param mapName name of the ressource to load
	 */
	public Map(int mapId, String mapName) {
		this.mapId = mapId;
		this.tmap = ResourceManager.getMap(mapName);
	}
	

	/**
	 * @return whether the map is a tiled map or an empty map
	 */
	public boolean isTiledMap() {
		if( tmap == null )
			return false;
		else
			return true;
	}
	
	
	/**
	 * Returns a vector containing the dimensions of a tile
	 * @return
	 */
	public Vector2f getTileDimension() {
		return new Vector2f(tmap.getTileWidth(), tmap.getTileHeight());
	}

	/**
	 * @return mapId of the map
	 */
	public int getMapId() {
		return this.mapId;
	}

	/**
	 * @return tiledMap
	 */
	public TiledMap getMap() {
		return tmap;
	}
	
	/**
	 * @return dimensions of the map in pixels
	 */
	public Vector2f getDimensions() {
		if(tmap == null )
			return new Vector2f(width, height);
		else
			return new Vector2f( tmap.getTileWidth() * tmap.getWidth(),  tmap.getTileHeight() * tmap.getHeight());
	}
}
