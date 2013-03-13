package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Map component
 * @author Alexandre
 *
 */
public class MapComponent extends Component {
	
	public int mapId;
	public TiledMap tmap;

	public MapComponent(int mapId, String mapName) {
		this.mapId = mapId;
		this.tmap = new TmxMapLoader().load(mapName);
	}
}
