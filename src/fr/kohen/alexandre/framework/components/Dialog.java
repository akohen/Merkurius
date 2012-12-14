package fr.kohen.alexandre.framework.components;

import java.util.ArrayList;
import java.util.HashMap;


import com.artemis.Component;

import fr.kohen.alexandre.framework.engine.ai.Reply;

/**
 * Allows the entity to enter dialog
 * @author Alexandre
 *
 */
public class Dialog extends Component {
	private int 	interactionTarget;
	private boolean canSpeak;
	private String 	name;
	private int 	lastSent;
	private int 	lastReceived;
	
	public Dialog(String name) {
		interactionTarget 	= -1;
		this.name 			= name;
		canSpeak 			= false;
	}

	public int getInteractionTarget() {
		return interactionTarget;
	}

	public void setInteractionTarget(int interactionTarget) {
		this.interactionTarget = interactionTarget;
	}

	/**
	 * Returns true is the entity is the player
	 * @return
	 */
	public boolean isPlayer() {
		if( name.equalsIgnoreCase("player") )
			return true;
		else return false;
	}
	
	/**
	 * Sends a message
	 * @return
	 */
	public int sendMessage(HashMap<Integer, Reply> replies) {
		ArrayList<Reply> possibleReplies = getReplies(replies);
		//TODO better reply selection
		lastSent = possibleReplies.get(0).getId();
		return lastSent;
	}
	
	/**
	 * Returns the list of possible replies
	 * @return replies
	 */
	public ArrayList<Reply> getReplies(HashMap<Integer, Reply> replies) {
		ArrayList<Reply> possibleReplies = new ArrayList<Reply> ();
		for( Reply reply : replies.values() ) {
			if( isPossible(reply) )
				possibleReplies.add(reply);
		}
		return possibleReplies;
	}
	
	/**
	 * Returns the last message received
	 * @return lastMessage
	 */
	public int getLastReceived() { return lastReceived; }
	
	public void setLastSent(int id) { lastSent = id; }
	
	public int getLastSent() { return lastSent; }
	
	/**
	 * Receives a message
	 * @param playerAnswer
	 */
	public void receiveMessage(int replyId) {
		lastReceived = replyId;
	}
	
	/**
	 * Returns true if the entity is waiting to speak
	 * @return
	 */
	public boolean canSpeak() {
		return canSpeak;
	}
	
	/**
	 * Sets if the entity can speak
	 * @param canSpeak
	 */
	public void setCanSpeak(boolean canSpeak) {
		this.canSpeak = canSpeak;
	}
	
	/**
	 * Checks whether the reply can be said by the entity
	 * @param reply
	 * @return true if the reply if possible
	 */
	public boolean isPossible(Reply reply) {
		if( reply.getReq("isPlayer") != null ) {
			if( reply.getReq("isPlayer").equalsIgnoreCase("true") && !isPlayer() )
				return false;
			if( reply.getReq("isPlayer").equalsIgnoreCase("false") && isPlayer() )
				return false;
		}
		
		if( reply.getReq("lastSent") != null ) {
			if( !reply.getReq("lastSent").equalsIgnoreCase("" + lastSent) )
				return false;
		}
		
		if( reply.getReq("lastReceived") != null ) {
			if( !reply.getReq("lastReceived").equalsIgnoreCase("" + lastReceived) )
				return false;
		}
		
		if( reply.getReq("name") != null ) {
			if( !reply.getReq("name").equalsIgnoreCase(name) )
				return false;
		}
		
		if( reply.getReq("isTrue") != null ) {
			if( !reply.getReq("name").equalsIgnoreCase(name) )
				return false;
		}
		
		
		return true;
	}

	/**
	 * Returns the actor's name
	 * @return name
	 */
	public String getName() {return name;}
	
}
