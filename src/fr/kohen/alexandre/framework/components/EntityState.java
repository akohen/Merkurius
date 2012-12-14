package fr.kohen.alexandre.framework.components;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.C.SELECTION_STATE;
import fr.kohen.alexandre.framework.engine.C.STATES;

import com.artemis.Component;

/**
 * Used to identify the player entity
 * @author Alexandre
 *
 */
public class EntityState extends Component {
	private STATES state;
	private SELECTION_STATE selectionState;
	
	public EntityState() {
		setState(C.STATES.IDLE);
		setSelectionState(C.SELECTION_STATE.INACTIVE);
	}

	public STATES getState() {
		return state;
	}

	public void setState(STATES state) {
		this.state = state;
	}

	public SELECTION_STATE getSelectionState() {
		return selectionState;
	}

	public void setSelectionState(SELECTION_STATE selectionState) {
		this.selectionState = selectionState;
	}
	
}
