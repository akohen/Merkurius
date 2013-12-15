package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;


/**
 * Holds the visual representation of the entity
 * @author Alexandre
 *
 */
public class VisualComponent extends Component implements Syncable {
	public String 	type 					= "";
	public String 	currentAnimationName 	= "";
	public float 	stateTime 				= 0f;
	
	
	public VisualComponent(String type) {
		this.type = type;
	}


	@Override
	public void sync(EntityUpdate update) {
		this.type 		= update.getNext();
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(type);
	}
}
