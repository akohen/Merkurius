package fr.kohen.alexandre.wip.multiplayerRogue.systems;

import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.systems.interfaces.SyncSystem;

public class MultiRogueChat extends VoidEntitySystem implements InputProcessor, ChatSystem {
	private boolean acceptingInput = true;
	private boolean chatEnabled = false;
	private StringBuffer chatText;
	private Entity chatEntity;
	private SyncSystem clientSystem;
	private int shift = 0;
	
	public MultiRogueChat() {
	}
	
	@Override
	public void initialize() {
		clientSystem = Systems.get(SyncSystem.class, world);
		chatEntity = world.createEntity()
			.addComponent( new Transform(-2, 0, 0, 10) )
			.addComponent( new TextComponent("chat") )
			.addComponent( new EntityState() );
		chatEntity.addToWorld();
		chatEntity.disable();
		
		chatText = chatEntity.getComponent(TextComponent.class).text;
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void processSystem() {
		toggleSystem();
	}


	private void toggleSystem() {
		if ( Gdx.input.isKeyPressed( Input.Keys.ENTER ) ) {
			if ( acceptingInput ) {
				acceptingInput 	= !acceptingInput;
				chatEnabled	= !chatEnabled;
				if( chatEnabled ) {
					systemIsEnabled();
				} else {	
					systemIsDisabled();
				}
			}
		} else {
			acceptingInput 	= true;
		}
	}


	private void systemIsEnabled() {
		Gdx.app.log("ChatSystem", "Chat enabled");	
		chatEntity.enable();
	}

	private void systemIsDisabled() {
		Gdx.app.log("ChatSystem", "Chat disabled");
		Gdx.app.log("ChatSystem", "typed: " + chatText);
		clientSystem.send("chat " + new String(chatText));
		chatEntity.disable();
		chatText.setLength(0);
	}


	@Override
	public boolean keyTyped(char character) {
		if( chatEnabled ) {
			chatText.append(character);
			return true;
		} else { return false; }
	}
	
	@Override
	public void newMessage(String message) {
		world.createEntity()
			.addComponent( new Transform(-1, 0, shift, 10) )
			.addComponent( new TextComponent(message) )
			.addComponent( new EntityState() )
			.addComponent( new Expires(5000) )
			.addToWorld();
		shift -= 15;
	}
	
	public int getCameraShift() {return shift; }
	
	@Override
	public boolean keyDown(int keycode) { return false; }

	@Override
	public boolean keyUp(int keycode) { return false; }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(int amount) { return false; }

	
	

}
