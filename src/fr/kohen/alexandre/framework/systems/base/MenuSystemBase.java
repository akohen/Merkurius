package fr.kohen.alexandre.framework.systems.base;

import java.util.ArrayList;
import java.util.Hashtable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import fr.kohen.alexandre.framework.components.Button;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.systems.interfaces.MenuSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MenuSystemBase extends EntityProcessingSystem implements KeyListener, MenuSystem {
	@Mapper ComponentMapper<Transform> 		transformMapper;
	@Mapper ComponentMapper<Button> 		buttonMapper;
	private GameContainer container;
	private Hashtable<String, ArrayList<Entity>> menuList;
	private Hashtable<String, Integer> activeButtons;
	private boolean returnPressed;
	private boolean moveUp;
	private boolean moveDown;
	private String activeGroup;
	private String action;

	@SuppressWarnings("unchecked")
	public MenuSystemBase(GameContainer container) {
		super( Aspect.getAspectForAll(Transform.class, Button.class) );
		this.container = container;
	}

	@Override
	public void initialize() {
		menuList 		= new Hashtable<String, ArrayList<Entity>>();
		activeButtons 	= new Hashtable<String, Integer>();
		activeGroup 	= null;
		action 			= null;
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e) {
		buttonMapper.get(e).render( transformMapper.get(e), isActive(e) );		
	}
	
	protected void begin() {
		action = null;
		if( moveDown ) {
			moveDown = false;
			activeButtons.put(activeGroup, (activeButtons.get(activeGroup)+1)%menuList.get(activeGroup).size());			
		}
		if( moveUp ) {
			moveUp = false;
			activeButtons.put(activeGroup, (activeButtons.get(activeGroup)+menuList.get(activeGroup).size()-1)%menuList.get(activeGroup).size());
		}
		if( returnPressed ) {
			returnPressed = false;
			action = buttonMapper.get( menuList.get(activeGroup).get(activeButtons.get(activeGroup)) ).getAction();			
		}
	}
	
	protected void inserted(Entity e) {
		ArrayList<Entity> menu;
		String group = this.buttonMapper.get(e).getGroup();
		if ( this.menuList.containsKey(group) ) {
			menu = this.menuList.get(group);
		} else {
			menu = new ArrayList<Entity>(); 		// Create a new menu group
			this.activeButtons.put(group, 0);		// Activate the first button							// Add the button to the group
		}
		menu.add(e);
		this.menuList.put(group, menu);
	}
	
	protected void removed(Entity e) {
		ArrayList<Entity> menu;
		String group = this.buttonMapper.get(e).getGroup();
		menu = this.menuList.get(group);
		menu.remove(e);
		if( menu.size() == 0 ) {
			this.menuList.remove(group);
			this.activeButtons.remove(group);
			if( activeGroup == group)
				activeGroup = null;
		}
		
	}
	
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_RETURN || key == Input.KEY_SPACE ) {
			this.returnPressed = true;
		} else if (key == Input.KEY_UP) {
			this.moveUp = true;
			this.moveDown = false;
		} else if (key == Input.KEY_DOWN) {
			this.moveUp = false;
			this.moveDown = true;
		}
		
	}
	
	private boolean isActive(Entity e) {
		String group = this.buttonMapper.get(e).getGroup();
		if ( activeButtons.get(group) == menuList.get(group).indexOf(e)  )
			return true;
		else
			return false;
	}
	
	/**
	 * The selected action
	 * @return
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * Resets the current action (so it's not used twice)
	 */
	public void resetAction() {
		action = null;
	}
	
	/**
	 * Activate the selected menu
	 * @param group
	 */
	public void setActiveMenu(String group) {
		this.activeGroup = group;
	}
	
	/**
	 * Disable the menu input
	 */
	public void disable() {
		this.setActiveMenu(null);
	}
	
	@Override
	public boolean isAcceptingInput() { 
		if( this.activeGroup == null )
			return false;
		else
			return true;
	}

	
	@Override
	public void keyReleased(int key, char c) {
	}

	@Override
	public void setInput(Input input) { }

	

	@Override
	public void inputEnded() { }

	@Override
	public void inputStarted() { }
}
