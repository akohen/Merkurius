package fr.kohen.alexandre.framework.model.actions;

import java.util.ArrayList;
import java.util.List;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.kohen.alexandre.framework.components.Mouse;
import fr.kohen.alexandre.framework.model.IAction;

public abstract class MouseAction implements IAction {

	protected ComponentMapper<Mouse> mouseMapper;
	protected List<Entity> mouseContacts = new ArrayList<Entity>();
	protected List<Entity> mousePressed = new ArrayList<Entity>();
	
	public MouseAction(World world) {
		mouseMapper 	= ComponentMapper.getFor(Mouse.class, world);
	}

	@Override
	public void beginContact(Entity e, Entity other, Contact contact) {
		if ( mouseMapper.has(other) ) {
			mouseContacts.add(other);
			mouseOver(e, other);	
		}
	}
	
	
	@Override
	public void process(Entity e) {	
		for( Entity mouse : mouseContacts ) {
			if ( mouseMapper.get(mouse).clicked ) {
				if ( !mousePressed.contains(mouse) ) {
					mousePressed.add(mouse);
					mouseClick(e, mouse);
				}
			} else {
				if ( mousePressed.contains(mouse) ) {
					mousePressed.remove(mouse);
					mouseRelease(e, mouse);
				}
			}
		}
		
	}
	
	@Override
	public void endContact(Entity e, Entity other, Contact contact) {
		if ( mouseContacts.contains(other) ) {
			mouseContacts.remove(other);
			mouseOff(e, other);
		}
		if ( mousePressed.contains(other) ) {
			mousePressed.remove(other);
		}
	}

	protected abstract void mouseClick(Entity e, Entity mouse);
	
	protected abstract void mouseRelease(Entity e, Entity mouse);
	
	protected abstract void mouseOver(Entity e, Entity mouse);
	
	protected abstract void mouseOff(Entity e, Entity mouse);

	

	

}
