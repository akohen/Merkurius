package fr.kohen.alexandre.examples.miniRPG.components;

import com.artemis.Component;

public class Test extends Component {
	private int id;
	
	public Test(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
