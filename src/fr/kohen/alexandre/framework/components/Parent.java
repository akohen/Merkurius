package fr.kohen.alexandre.framework.components;

import com.artemis.Component;

/**
 * Used to identify an entity's parent entity
 * @author Alexandre
 *
 */
public class Parent extends Component {
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

}
