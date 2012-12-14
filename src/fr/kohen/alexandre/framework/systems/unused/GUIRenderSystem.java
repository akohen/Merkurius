package fr.kohen.alexandre.framework.systems.unused;

import org.newdawn.slick.Color;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Unused;
import fr.kohen.alexandre.framework.spatials.BoxSpatial;


import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class GUIRenderSystem extends EntityProcessingSystem {

	private Entity stateDisplay;
	private Entity stateDisplay2;

	private ComponentMapper<EntityState> 	stateMapper;
	private ComponentMapper<SpatialForm> 	spatialMapper;

	@SuppressWarnings("unchecked")
	public GUIRenderSystem() {
		super(Unused.class);
	}

	@Override
	public void initialize() {
		spatialMapper	= new ComponentMapper<SpatialForm>	(SpatialForm.class, world);
		stateMapper 	= new ComponentMapper<EntityState>	(EntityState.class, world);
		guiSetup();
	}

	public void begin() {
		spatialMapper.get(stateDisplay).getSpatial().setText(
				stateMapper.get(world.getTagManager().getEntity("player")).getState().toString() );
		
		spatialMapper.get(stateDisplay2).getSpatial().setText(
						stateMapper.get(world.getEntity(5)).getState().toString() );
	}
	
	/**
	 * Create the main GUI entities
	 */
	private void guiSetup() {
		Entity e = EntityFactory.createGuiText(world, 75, 75, "");
		e.setTag("StateDisplay");
		stateDisplay = e;

		e = EntityFactory.createGuiText(world, 175, 75, "");
		e.setTag("StateDisplay2");
		stateDisplay2 = e;
		
		e = world.createEntity();
		e.setGroup("GUI");
		e.setTag("Gui1");
		e.addComponent(new Transform(100, 10));
		e.addComponent(new SpatialForm(new BoxSpatial(75, 25)));
		spatialMapper.get(e).getSpatial().setColor(new Color(0, 0, 255, 55));
		e.refresh();
	}

	@Override
	protected void process(Entity e) { }
}
