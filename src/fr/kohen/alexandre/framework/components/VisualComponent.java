package fr.kohen.alexandre.framework.components;

import com.artemis.Component;


/**
 * Holds the visual representation of the entity
 * @author Alexandre
 *
 */
public class VisualComponent extends Component {
	public String 	type 					= "";
	public String 	currentAnimationName 	= "";
	public float 	stateTime 				= 0f;
	public int		depth					= 0;
	
	
	public VisualComponent(String type) {
		this.type = type;
	}
}
