package fr.kohen.alexandre.framework.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;

/**
 * libGDX keyboard input wrapper. Used to provide user-defined controls.
 * Should be able to load key mappings from a file.
 * May evolve in a complete input wrapper
 * @author Alexandre
 */
public class KeyBindings {
	private static Map<String, List<Integer>> mappedKeys = new HashMap<String, List<Integer>>();
	private static List<Integer> keys = new ArrayList<Integer>();
	private static List<String> actions = new ArrayList<String>();
	private static Entry<String, List<Integer>> entry;
	
	
	/**
	 * Removes all the bindings
	 */
	public static void removeAll() {
		mappedKeys = new HashMap<String, List<Integer>>();
	}
	
	
	/**
	 * Adds a key binding to an action
	 * @param key
	 * @param action
	 */
	public static void addKey(int key, String action) {
		keys = mappedKeys.get(action);
		if ( keys == null ) { 
			keys = new ArrayList<Integer>(); 
		}
		keys.add(key);
		mappedKeys.put(action, keys);
	}
	
	
	/**
	 * 
	 * @param action
	 * @return
	 */
	public static List<Integer> getKeys(String action) {
		return mappedKeys.get(action);
	}
	
	
	/**
	 * @param key
	 * @return true if the key is mapped to at least an action
	 */
	public static boolean isKeyUsed(int key) {
		return !getActionsForKey(key).isEmpty();
	}
	
	
	public static List<String> getActionsForKey(int key) {
		actions.clear();
		Iterator<Entry<String, List<Integer>>> it = mappedKeys.entrySet().iterator();
	    while (it.hasNext()) {
	    	entry = it.next();
	    	keys = entry.getValue();
	        if( keys.contains(key) ) {
	        	actions.add( entry.getKey() );
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return actions;
	}
	
	
	public static boolean isActionDefined(String action) {
		return mappedKeys.containsKey(action);
	}
	
	
	/**
	 * Checks if the defined key for the action is pressed
	 * @param action
	 * @return
	 */
	public static boolean isKeyPressed(String action) {
		if( isActionDefined(action) ) {
			for( int key : getKeys(action) ) {
				if( Gdx.input.isKeyPressed(key) ) return true;
			}
		}
		return false;
	}

	
}
