package fr.kohen.alexandre.games.space.actions;

import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.engine.ActionList;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.CommunicationSystem;

public class ClientHeadingActions extends ActionList {
	private String direction;
	private CommunicationSystem commSystem;
	
	public ClientHeadingActions(World world, String direction) {
		super(world);
		this.direction = direction;
		commSystem = Systems.get(CommunicationSystem.class, world);
	}

	public void onMouseClick(Entity e) {
		commSystem.send("turn " + direction);
	}
	
	public void onMouseHover(Entity e) { }
	
	public void onMouseOff(Entity e) { }
	
	public void onActivate(Entity e) { }
	
	public void onSelect(Entity e) { }
	
	public void onDeselect(Entity e) { }
}
