package fr.kohen.alexandre.framework.model.actions;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.kohen.alexandre.framework.model.Action;

public class ActionList implements Action {
	private List<Action> actions = new ArrayList<Action>();
	
	public ActionList() {}
	
	public ActionList(List<Action> actions) { this.actions = actions; }
	
	public ActionList(Action action) { this.add(action); }
	
	public void add(Action action) { this.actions.add(action); }
	
	public void remove(Action action) { this.actions.remove(action); }
	
	@Override
	public void beginContact(Entity e, Entity other, Contact contact) {
		for ( Action action : actions ) {
			action.beginContact(e, other, contact);
		}
	}

	@Override
	public void endContact(Entity e, Entity other, Contact contact) {
		for ( Action action : actions ) {
			action.endContact(e, other, contact);
		}
	}

	@Override
	public void process(Entity e) {
		for ( Action action : actions ) {
			action.process(e);
		}
	}
}
