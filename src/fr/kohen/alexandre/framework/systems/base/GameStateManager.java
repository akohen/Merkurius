package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;

import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.systems.interfaces.MenuSystem;

public class GameStateManager extends VoidEntitySystem {
	private StateBasedGame 	sb;
	private MenuSystem 		menuSystem;
	private GameContainer 	container;
	private String			state = C.STATE_INIT;

	public GameStateManager(GameContainer container, StateBasedGame sb) {
		super();
		this.sb = sb;
		this.container = container;
	}

	@Override
	public void initialize() {
		menuSystem		= Systems.get(MenuSystem.class, world);
	}
	
	
	public String 	getState() 				{ return state; }
	public void 	setState(String state) 	{ this.state = state; }

	@Override
	protected void processSystem() {
		if ( this.menuSystem.getAction() != null  )
			if( this.menuSystem.getAction().equalsIgnoreCase(C.ACTION_ENTERGAMESTATE) ) {
				sb.enterState(C.GAME_STATE);
			}
			else if( this.menuSystem.getAction().equalsIgnoreCase(C.ACTION_EXIT) ) {
				container.exit();
			}
		
		if( world.getManager(TagManager.class).getEntity("fade_out") != null )
			state = C.STATE_FADE_OUT;
		
		if( state.equalsIgnoreCase(C.STATE_FADE_OUT) && world.getManager(TagManager.class).getEntity("fade_out") == null ) {
			state = C.STATE_PLAYING;
		}
		
		if( state.equalsIgnoreCase(C.STATE_EXIT) ) { container.exit(); }
		
	}
}	