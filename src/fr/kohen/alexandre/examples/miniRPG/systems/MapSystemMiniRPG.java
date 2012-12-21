package fr.kohen.alexandre.examples.miniRPG.systems;


import org.newdawn.slick.tiled.TiledMap;

import fr.kohen.alexandre.examples.miniRPG.EntityFactoryMiniRPG;
import fr.kohen.alexandre.framework.systems.base.MapSystemBase;

public class MapSystemMiniRPG extends MapSystemBase {

	public MapSystemMiniRPG() {
		super();
	}


	public void checkTile(int tileX, int tileY, int layer, TiledMap tmap, int mapId) {
		if ( 0 < tmap.getTileId(tileX, tileY, layer) ) { 
			EntityFactoryMiniRPG.createObstacle(
					world, 
					mapId, 
					tileX * tmap.getTileWidth(), 
					tileY * tmap.getTileHeight(), 
					tmap.getTileWidth(), 
					tmap.getTileHeight() );
		}
	}
}
