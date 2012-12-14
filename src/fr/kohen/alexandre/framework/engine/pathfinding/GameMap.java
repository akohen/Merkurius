package fr.kohen.alexandre.framework.engine.pathfinding;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import fr.kohen.alexandre.framework.components.Map;

/**
 * Map for local pathfinding
 * @author Alexandre
 *
 */
public class GameMap implements TileBasedMap {
	private int widthInTiles, heightInTiles;
	private Map map;

	public GameMap(Map localMap) {
		this.widthInTiles 	= localMap.getMap().getWidth();
		this.heightInTiles 	= localMap.getMap().getHeight();
		this.map = localMap;
	}

	@Override
	public int getWidthInTiles() {
		return widthInTiles;
	}

	@Override
	public int getHeightInTiles() {
		return heightInTiles;
	}

	@Override
	public void pathFinderVisited(int x, int y) {		
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		if( -1 < this.map.getMap().getLayerIndex("collision") ) // Checking if the map has a collision layer
			for( int x=0; x<getXTiles(context.getMover()); x++ )
				for( int y=0; y<getYTiles(context.getMover()); y++ )
					if ( tx+x < this.widthInTiles && ty+y < this.heightInTiles ) // Checks that the tile is in the map
						if ( 0 < this.map.getMap().getTileId(tx+x, ty+y, this.map.getMap().getLayerIndex("collision")) ) // Checking for an obstacle at this position
							return true; // Obstacle found

		//TODO: Checks objects on the map
		return false;
	}
	
	private int getXTiles(Mover mover) {
		int width = ((UnitMover) mover).getWidth(); // Width of the moving object
		int sizeX = (int) ((UnitMover) mover).getTileDimension().x; // size of a tile
		return (int) Math.ceil((float) width/sizeX);
	}
	
	private int getYTiles(Mover mover) {
		int height = ((UnitMover) mover).getHeight(); // Width of the moving object
		int sizeY = (int) ((UnitMover) mover).getTileDimension().y; // size of a tile
		return (int) Math.ceil((float)height/sizeY);
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1;
	}

}
