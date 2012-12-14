package fr.kohen.alexandre.framework.systems.interfaces;

public interface MenuSystem {
	
	/**
	 * Activate the selected menu
	 * @param group
	 */
	public void setActiveMenu(String group);
	
	
	/**
	 * Disable the menu input
	 */
	public void disable();
	
	
	/**
	 * The selected action
	 * @return
	 */
	public String getAction();
	
	
	/**
	 * Resets the current action (so it's not used twice)
	 */
	public void resetAction();
}
