package fr.kohen.alexandre.framework.systems.interfaces;

public interface ScriptSystem {
	
	/**
	 * Charge les scripts du fichier fileName
	 * @param fileName
	 */
	public void loadFile(String fileName);
	

    /**
     * Executes the given string
     * @param cmd
     */
    public String exec(String cmd);
    
    
}
