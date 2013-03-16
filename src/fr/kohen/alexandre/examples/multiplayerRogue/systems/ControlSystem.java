package fr.kohen.alexandre.examples.multiplayerRogue.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import fr.kohen.alexandre.examples.multiplayerRogue.components.*;
import fr.kohen.alexandre.framework.components.Player;

public class ControlSystem extends EntityProcessingSystem {
	private ComponentMapper<Input> inputMapper;

	@SuppressWarnings("unchecked")
	public ControlSystem() {
		super( Aspect.getAspectForAll( Player.class, Input.class) );
	}
	
	public void initialize() {
		this.inputMapper = ComponentMapper.getFor(Input.class, world);
	}

	@Override
	protected void process(Entity player) {
		if ( Gdx.input.isKeyPressed(Keys.UP) ) {
			inputMapper.get(player).input1 = 1;
			//player.getComponent(Destination.class).position.y += 50;
		} else if ( Gdx.input.isKeyPressed(Keys.RIGHT) ) {
			inputMapper.get(player).input1 = 2;
		} else if ( Gdx.input.isKeyPressed(Keys.DOWN) ) {
			inputMapper.get(player).input1 = 3;
			//player.getComponent(Destination.class).position.y -= 50;
		} else if ( Gdx.input.isKeyPressed(Keys.LEFT) ) {
			inputMapper.get(player).input1 = 4;
		} else {
			inputMapper.get(player).input1 = 0;
		}
	}

}
