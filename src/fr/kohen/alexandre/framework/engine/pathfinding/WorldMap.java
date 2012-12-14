package fr.kohen.alexandre.framework.engine.pathfinding;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class WorldMap implements TileBasedMap {

	public WorldMap() {
	}

	@Override
	public int getWidthInTiles() {
		return 200;
	}

	@Override
	public int getHeightInTiles() {
		return 200;
	}

	@Override
	public void pathFinderVisited(int x, int y) {		
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1;
	}

}
