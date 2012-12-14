package fr.kohen.alexandre.framework.components;

import java.util.ArrayList;

import com.artemis.Component;

/**
 * Allows the entity to carry items
 * @author Alexandre
 *
 */
public class Inventory extends Component {
	private ArrayList<Item> inventory;
	
	public Inventory() {
		inventory = new ArrayList<Item>();
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
}
