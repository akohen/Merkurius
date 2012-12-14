package fr.kohen.alexandre.framework.engine.ai;

import java.util.HashMap;

import org.newdawn.slick.util.Log;

public class Reply implements Comparable<Reply> {
	private String 					text;
	private int 					id;
	private HashMap<String,String> 	requirements;
	private	boolean					stopDialog;
	private	String					script;
	
	public Reply(int id, String text) {
		this.id 			= id;
		this.text 			= text;
		this.requirements 	= new HashMap<String,String>();
		this.stopDialog		= false;
		this.script			= null;
		Log.info("[Reply] id: " + id + " - " + text);
	}
	
	public String toString() { return text;}
	
	public int getId() {return id;}
	
	public String getText() { return text;}
	
	public void addReq(String key, String value) {
		requirements.put(key, value);
		Log.info("[Reply]    requires: " + key + " = " + value);
	}
	
	/**
	 * Gets requirement with key "key"
	 * @param key
	 * @return
	 */
	public String getReq(String key) {
		return requirements.get(key);
	}
	
	public void setStopDialog(String stop) {
		if( stop != null && stop.equalsIgnoreCase("true") ) {
			this.stopDialog = true;
			Log.info("[Reply]    stop: " + stop );
		}			
	}
	
	/**
	 * Returns true is the reply stops the dialog, false otherwise
	 * @return
	 */
	public boolean getStopDialog() {
		return stopDialog;
	}
	
	public void setScript(String script) {
		if( script != null ) {
			this.script = script;
			Log.info("[Reply]    script: " + script );
		}	
	}
	
	/**
	 * Returns the script to execute, or null
	 * @return
	 */
	public String getScript() {
		return script;
	}

	@Override
	public int compareTo(Reply reply) {
		if( reply.getId() < this.getId() )
			return 1;
		else return -1;
	}

}
