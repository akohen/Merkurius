package fr.kohen.alexandre.games.space.actions;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.components.Destination;
import fr.kohen.alexandre.framework.engine.ActionList;

public class IncreaseHeadingActions extends ActionList {

	protected ComponentMapper<Destination> 	destinationMapper;
	protected Entity ship;
	private String direction;
	
	public IncreaseHeadingActions(World world, Entity ship, String direction) {
		super(world);
		this.ship = ship;
		this.direction = direction;
		destinationMapper 	= new ComponentMapper<Destination>(Destination.class, world);
	}

	public void onMouseClick(Entity e) {
		if( direction.equalsIgnoreCase("left") )
			destinationMapper.get(ship).addRotation(-45);
		else if( direction.equalsIgnoreCase("right") )
			destinationMapper.get(ship).addRotation(45);
	}
	
	public void onMouseHover(Entity e) { 
	}
	
	public void onMouseOff(Entity e) { }
	
	public void onActivate(Entity e) { }
	
	public void onSelect(Entity e) { }
	
	public void onDeselect(Entity e) { }
}
