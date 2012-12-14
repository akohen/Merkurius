package fr.kohen.alexandre.framework.systems.base;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.Unused;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;
import fr.kohen.alexandre.framework.systems.interfaces.ScriptSystem;



public class ScriptSystemBase extends EntityProcessingSystem implements ScriptSystem {
	protected 	boolean 						consolePrinted;
	protected 	Context 						scriptContext;
	protected 	ScriptableObject 				gameProxy;
	protected	HashMap<String, String>			waitingScripts;
	protected 	GameContainer 					container;
	protected 	Class<?> 						scriptClass;

	
	@SuppressWarnings("unchecked")
	public ScriptSystemBase(GameContainer container) {
		super(Unused.class);
		this.container = container;
		this.scriptClass = ScriptProxy.class;
	}

	@Override
	public void initialize() {
		waitingScripts	= new HashMap<String, String>();		
		
		// Creating instance of the game proxy
		try { gameProxy = (ScriptableObject) scriptClass.getConstructor(this.getClass()).newInstance(this); } 
		catch (Exception e) { e.printStackTrace(); }
    	
		// Creating context
    	scriptContext 	= Context.enter();
    	
    	// Loading all functions
    	int methodsNb 	= scriptClass.getDeclaredMethods().length;
    	int i 			= 0;
    	String[] scriptAvailableFunctions = new String[methodsNb-1];
    	
    	for( Method method : scriptClass.getDeclaredMethods() ) {
    		if( !method.getName().equalsIgnoreCase("getClassName") ) {
    			scriptAvailableFunctions[i++] = method.getName();
        		Log.info( "[ScriptSystem] Added '" + method.getName() + "' function to javascript" );
    		}
    	}
    	gameProxy.defineFunctionProperties(scriptAvailableFunctions, scriptClass, ScriptableObject.DONTENUM);
    	
    	consolePrinted = false;
    	
    	// Loading javascript file
    	//loadFile(ResourceManager.getFile("script"));
	}
	
	protected class ScriptProxy extends ScriptableObject {

		private static final long serialVersionUID = 1338190032971566895L;
		public ScriptProxy() {super();}
		
		@Override
		public String getClassName() { return "global"; }
		
		public void log(String message) { Log.info(message); }
		public void reload() { loadFile(ResourceManager.getFile("script")); }
		public void exit() { container.exit(); }
		
		
		/**
    	 * Charge la carte mapName avec l'id mapId
    	 * @param mapId
    	 * @param mapName
    	 */
    	public void loadMap(int mapId, String mapName) { EntityFactory.createMap(world, mapId, mapName); }
		
		/**
    	 * Executes a command after some time
    	 * @param duration
    	 * @param cmd
    	 */
    	public void waitAndExec(int duration, String tag, String cmd) {
    		EntityFactory.createTimer(world, tag, duration);
    		waitingScripts.put(tag, cmd);
    	}
    }
	
	
    /**
     * Executes the given string
     * @param cmd
     */
    public String exec(String cmd) {
    	Object result = scriptContext.evaluateString(gameProxy, cmd, "<exec>", 1, null);
    	return Context.toString(result);
    }
    
	@Override
	protected void process(Entity e) {
	}
	
	protected void begin() {
		if(!consolePrinted) {
    	    System.out.print("> ");
    	    consolePrinted = true;
    	}

		try {
			if(System.in.available() != 0) {
			    byte[] b = new byte[System.in.available()];
			    System.in.read(b);
			    String s = new String(b);
			    
			    Object result = scriptContext.evaluateString(gameProxy, s, "<cmd>", 1, null);
			    System.out.println(Context.toString(result));
			    consolePrinted = false;
			}
		} catch (IOException e1) { e1.printStackTrace(); }		
	}
	
	protected void end() {
		ArrayList<String> expiredScripts = new ArrayList<String>();
		
		for( String tag : waitingScripts.keySet() ) {
			if( world.getTagManager().getEntity(tag) == null ) {
				exec(waitingScripts.get(tag));
				expiredScripts.add(tag);
			}
		}
		
		for( String tag : expiredScripts )
			waitingScripts.remove(tag);
	}
	
	/**
	 * Charge les scripts du fichier fileName
	 * @param fileName
	 */
	public void loadFile(String fileName) {
		InputStream file 		= ResourceLoader.getResourceAsStream(fileName);
        BufferedInputStream bin = new BufferedInputStream(file);
        byte[] 		contents	= new byte[1024];       
        int 		bytesRead	= 0;
        String 		source 		= new String();       
        try {
			while( (bytesRead = bin.read(contents)) != -1){			       
			        source += new String(contents, 0, bytesRead); }
		} catch (IOException e1) { e1.printStackTrace(); }
        scriptContext.evaluateString(gameProxy, source, "<source>", 1, null);
	}
}