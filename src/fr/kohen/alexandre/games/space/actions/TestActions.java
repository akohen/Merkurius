package fr.kohen.alexandre.games.space.actions;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.engine.ActionList;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;

public class TestActions extends ActionList {

	protected ComponentMapper<SpatialForm> 	spatialMapper;
	protected boolean mouseOff = true;
	
	public TestActions(World world) { 
		super(world);
		spatialMapper 	= new ComponentMapper<SpatialForm>(SpatialForm.class, world);
	}

	public void onMouseClick(Entity e) {
		spatialMapper.get(e).setSpatial(new BoxSpatial(25,25));
		mouseOff = false;
	}
	
	public void onMouseHover(Entity e) {

	}
	
	public void onMouseOff(Entity e) { }
	
	public void onActivate(Entity e) { }
	
	public void onSelect(Entity e) { }
	
	public void onDeselect(Entity e) { }
}
