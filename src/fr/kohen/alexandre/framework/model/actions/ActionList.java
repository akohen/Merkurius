package fr.kohen.alexandre.framework.model.actions;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.kohen.alexandre.framework.model.IAction;

public class ActionList implements IAction {
	private List<IAction> actions = new ArrayList<IAction>();
	
	public ActionList() {}
	
	public ActionList(List<IAction> actions) { this.actions = actions; }
	
	public ActionList(IAction action) { this.add(action); }
	
	public void add(IAction action) { this.actions.add(action); }
	
	public void remove(IAction action) { this.actions.remove(action); }
	
	@Override
	public void beginContact(Entity e, Entity other, Contact contact) {
		for ( IAction action : actions ) {
			action.beginContact(e, other, contact);
		}
	}

	@Override
	public void endContact(Entity e, Entity other, Contact contact) {
		for ( IAction action : actions ) {
			action.endContact(e, other, contact);
		}
	}

	@Override
	public void process(Entity e) {
		for ( IAction action : actions ) {
			action.process(e);
		}
	}
}
