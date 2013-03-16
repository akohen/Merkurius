package fr.kohen.alexandre.examples.screens;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.model.actions.MouseAction;
import fr.kohen.alexandre.framework.systems.interfaces.*;

public class ScreenExampleAction extends MouseAction {

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		Systems.get(ScreenSystem.class, world).setScreen("mainScreen");
	}

	@Override
	protected void mouseRelease(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOver(Entity e, Entity mouse) {
		Systems.get(VisualDrawSystem.class, world).setVisual(e, "example_box_green_50");
	}

	@Override
	protected void mouseOff(Entity e, Entity mouse) {
		Systems.get(VisualDrawSystem.class, world).setVisual(e, "example_box_50");
	}
	
}
