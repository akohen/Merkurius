package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Holds the reactions of the entity to events
 * @author Alexandre
 *
 */
public class ActionsComponent extends Component {
	public String type = "";
	
	public ActionsComponent(String type) { this.type = type; }
}
