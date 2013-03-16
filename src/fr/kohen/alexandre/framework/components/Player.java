package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Used to identify the player entity
 * @author Alexandre
 *
 */
public class Player extends Component {
	public int playerId = -1;
	
	public Player() {
	}
	
	public Player(int playerId) { this.playerId = playerId; }
}
