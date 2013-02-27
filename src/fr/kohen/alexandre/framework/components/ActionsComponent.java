package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.model.IAction;

/**
 * Holds the reactions of the entity to events
 * @author Alexandre
 *
 */
public class ActionsComponent extends Component {
	public IAction action;
	
	public ActionsComponent(IAction action) {
		this.action = action;
	}
}
