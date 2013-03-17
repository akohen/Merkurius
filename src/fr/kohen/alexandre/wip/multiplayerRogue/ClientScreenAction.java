package fr.kohen.alexandre.wip.multiplayerRogue;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.model.actions.MouseAction;
import fr.kohen.alexandre.framework.systems.interfaces.*;

public class ClientScreenAction extends MouseAction {

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		Systems.get(ScreenSystem.class, world).setScreen("clientScreen");
	}

	@Override
	protected void mouseRelease(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOver(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOff(Entity e, Entity mouse) {
	}
	
}
