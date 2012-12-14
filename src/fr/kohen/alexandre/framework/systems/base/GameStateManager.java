package fr.kohen.alexandre.framework.systems.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.components.Unused;
import fr.kohen.alexandre.framework.engine.C;

public class GameStateManager extends EntityProcessingSystem {
	private StateBasedGame 	sb;
	private MenuSystemBase 	menuSystem;
	private GameContainer 	container;
	private String			state = C.STATE_INIT;

	@SuppressWarnings("unchecked")
	public GameStateManager(GameContainer container, StateBasedGame sb) {
		super(Unused.class);
		this.sb = sb;
		this.container = container;
	}

	@Override
	public void initialize() {
		this.menuSystem = world.getSystemManager().getSystem(MenuSystemBase.class);
	}
	
	protected void begin() {
		if ( this.menuSystem.getAction() != null  )
			if( this.menuSystem.getAction().equalsIgnoreCase(C.ACTION_ENTERGAMESTATE) ) {
				sb.enterState(C.GAME_STATE);
			}
			else if( this.menuSystem.getAction().equalsIgnoreCase(C.ACTION_EXIT) ) {
				container.exit();
			}
		
		if( world.getTagManager().getEntity("fade_out") != null )
			state = C.STATE_FADE_OUT;
		
		if( state.equalsIgnoreCase(C.STATE_FADE_OUT) && world.getTagManager().getEntity("fade_out") == null ) {
			state = C.STATE_PLAYING;
		}
		
		if( state.equalsIgnoreCase(C.STATE_EXIT) ) { container.exit(); }
		
			
		
	}

	@Override
	protected void process(Entity e) {
		
	}
	
	public String 	getState() 				{ return state; }
	public void 	setState(String state) 	{ this.state = state; }
}	