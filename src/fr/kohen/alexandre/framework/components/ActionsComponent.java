package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.model.Action;

/**
 * Holds the reactions of the entity to events
 * @author Alexandre
 *
 */
public class ActionsComponent extends Component {
	public Action action;
	
	public ActionsComponent(Action action) {
		this.action = action;
	}
}
