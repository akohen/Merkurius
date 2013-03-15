package fr.kohen.alexandre.examples.mouse;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.model.actions.RightMouseAction;

public class ExampleActionRight extends RightMouseAction {

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		Gdx.app.log("RightMouseAction", "mouse click");
	}

	@Override
	protected void mouseRelease(Entity e, Entity mouse) {
		Gdx.app.log("RightMouseAction", "mouse release");
	}

	@Override
	protected void mouseOver(Entity e, Entity mouse) {
		Gdx.app.log("RightMouseAction", "mouse over");
	}

	@Override
	protected void mouseOff(Entity e, Entity mouse) {
		Gdx.app.log("RightMouseAction", "mouse off");
	}
	
}
