package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.engine.ActionList;

/**
 * Actions on the entity
 * @author Alexandre
 *
 */
public class Actions extends Component {
	
	private ActionList actionList;

	public Actions(ActionList actionList) { this.setActionList(actionList); }

	public ActionList getActionList() { return actionList; }

	public void setActionList(ActionList actionList) { this.actionList = actionList; }
	
}
