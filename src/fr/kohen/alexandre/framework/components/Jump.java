package fr.kohen.alexandre.framework.components;

import com.artemis.Component;
/**
 * Allows entities to jump
 * @author Alexandre
 *
 */
public class Jump extends Component {
	private boolean onGround;
	
	public Jump() { onGround = false; }
	
	public boolean canJump() { return onGround; }
	
	public void setCanJump(boolean canJump) { onGround = canJump;}
}
