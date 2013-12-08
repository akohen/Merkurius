package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

/**
 * Used to identify the player entity
 * @author Alexandre
 *
 */
public class Player extends Component implements Syncable {
	public int playerId = -1;
	
	public Player() {
	}
	
	public Player(int playerId) { this.playerId = playerId; }

	@Override
	public void sync(EntityUpdate update) {
		this.playerId 		= update.getNextInteger();
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(playerId);
	}
}
