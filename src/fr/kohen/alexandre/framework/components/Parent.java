package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

import fr.kohen.alexandre.framework.network.Syncable;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem.EntityUpdate;

/**
 * Used to identify an entity's parent entity
 * @author Alexandre
 *
 */
public class Parent extends Component implements Syncable{
	public int parentId;
	
	public Parent(int parentId) {
		this.setParentId(parentId);
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public void sync(EntityUpdate update) {
		this.parentId = update.getNextInteger();		
	}

	@Override
	public StringBuilder getMessage() {
		return new StringBuilder().append(parentId);
	}

}
