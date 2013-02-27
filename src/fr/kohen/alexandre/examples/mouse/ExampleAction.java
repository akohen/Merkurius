package fr.kohen.alexandre.examples.mouse;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.model.actions.MouseAction;

public class ExampleAction extends MouseAction {

	public ExampleAction(World world) {
		super(world);
	}

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		Gdx.app.log("MouseAction", "mouse click");
	}

	@Override
	protected void mouseRelease(Entity e, Entity mouse) {
		Gdx.app.log("MouseAction", "mouse release");
	}

	@Override
	protected void mouseOver(Entity e, Entity mouse) {
		Gdx.app.log("MouseAction", "mouse over");
	}

	@Override
	protected void mouseOff(Entity e, Entity mouse) {
		Gdx.app.log("MouseAction", "mouse off");
	}

}
