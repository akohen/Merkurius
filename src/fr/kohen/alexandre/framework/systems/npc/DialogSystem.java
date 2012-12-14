package fr.kohen.alexandre.framework.systems.npc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.Bag;

import fr.kohen.alexandre.framework.EntityFactory;
import fr.kohen.alexandre.framework.components.Dialog;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.SpatialForm;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.C;
import fr.kohen.alexandre.framework.engine.Systems;
import fr.kohen.alexandre.framework.engine.ai.Reply;
import fr.kohen.alexandre.framework.engine.parsers.RepliesParser;
import fr.kohen.alexandre.framework.spatials.DialogBox;
import fr.kohen.alexandre.framework.systems.base.MenuSystemBase;
import fr.kohen.alexandre.framework.systems.interfaces.MenuSystem;
import fr.kohen.alexandre.framework.systems.interfaces.ScriptSystem;


public class DialogSystem extends EntityProcessingSystem {
	private ComponentMapper<Dialog> 		dialogMapper;
	private ComponentMapper<EntityState> 	stateMapper;
	private Entity 							dialogBox;
	private Bag<Entity> 					dialogEntities;
	private boolean 						playerTalking;
	private boolean 						dialogDisplayed;
	private MenuSystem 						menuSystem;
	private ScriptSystem					scriptSystem;
	private HashMap<Integer, Reply>			replies;


	@SuppressWarnings("unchecked")
	public DialogSystem() {
		super(Dialog.class, EntityState.class);
	}

	@Override
	public void initialize() {
		menuSystem 		= Systems.get						(MenuSystem.class, 	world);
		scriptSystem 	= Systems.get						(ScriptSystem.class, 	world);
		dialogMapper 	= new ComponentMapper<Dialog>		(Dialog.class, world);
		stateMapper 	= new ComponentMapper<EntityState>	(EntityState.class, world);
		dialogBox 		= null;
		dialogEntities	= new Bag<Entity>();
		replies			= new RepliesParser().getReplies();
		dialogDisplayed = false;
	}
	
	protected void begin() {
		playerTalking = false;
	}
	
	protected void end() {
		if (playerTalking == false)
			dialogGuiRemove();
	}
	
	@Override
	protected void process(Entity e) {
		if( stateMapper.get(e).getState() == C.STATES.TALKING ) { //
			Dialog dialog = dialogMapper.get(e);
			
			if( dialog.isPlayer() ) { // The player is talking
				dialogGuiSetup(); // Setting up dialog GUI if the player is talking
				
				Dialog correspondent = dialogMapper.get(world.getEntity(dialog.getInteractionTarget()));
				
				// Displaying the last message sent to the player
				if( dialog.getLastReceived() > 0 && !dialogDisplayed ){
					dialogEntities.add( e = EntityFactory.createGuiText( world, 140, 235, 150, "" + replies.get(dialog.getLastReceived()) ) );
					dialogDisplayed = true;
				}
				
				// Setting up anwser choices for the player
				if( dialog.canSpeak() ) {
					ArrayList<Reply> dialogReplies = getMessages(dialog, correspondent);
					for( int i=0; i<dialogReplies.size(); i++) {
						String option = dialogReplies.get(i).getText();
						dialogEntities.add(EntityFactory.createButton(
								world, 
								140, 
								280+28*i, 
								option, 
								"dialog", 
								"dialog option " + dialogReplies.get(i).getId() ));
					}
					world.getSystemManager().getSystem(MenuSystemBase.class).setActiveMenu("dialog");
					dialog.setCanSpeak(false);
				}
				
				// Sending answer to other entity
				Integer playerAnswer = null;
				if( menuSystem.getAction() != null && menuSystem.getAction().contains("dialog option ") ) { // Answer selected
					playerAnswer = Integer.decode( menuSystem.getAction().substring(14) );
					menuSystem.resetAction(); // resetting choice
					removeChoices(); // Removing old choices
					correspondent.receiveMessage(playerAnswer); // Sending message
					correspondent.setCanSpeak(true);
					dialogDisplayed = false;
					replyActions(e, playerAnswer);
				}
				
				playerTalking = true;
			}
			else { // handling NPC dialog
				if( dialog.canSpeak() ) { sendMessage(dialog, getTarget(dialog) ); }
			}			
		}
	}
	
	
	/**
	 * Selects and sends a message from sender to receiver
	 * @param sender
	 * @param receiver
	 */
	public void sendMessage(Dialog sender, Dialog receiver) {
		int answer = getMessages(sender, receiver).get(0).getId();
		sender.setLastSent(answer);
		sender.setCanSpeak(false);
		receiver.receiveMessage(answer);
		receiver.setCanSpeak(true);
		replyActions(world.getEntity(receiver.getInteractionTarget()), answer);
	}
	

	/**
	 * Returns all possible messages
	 * @param sender
	 * @return
	 */
	public ArrayList<Reply> getMessages(Dialog sender, Dialog receiver) {
		ArrayList<Reply> possibleReplies = new ArrayList<Reply> ();
		for( Reply reply : replies.values() ) {
			boolean possible = true;
			if( reply.getReq("isPlayer") != null ) {
				if( reply.getReq("isPlayer").equalsIgnoreCase("true") && !sender.isPlayer() )
					possible = false;
				if( reply.getReq("isPlayer").equalsIgnoreCase("false") && sender.isPlayer() )
					possible = false;
			}
			if( reply.getReq("lastSent") != null ) {
				if( !reply.getReq("lastSent").equalsIgnoreCase("" + sender.getLastSent()) )
					possible = false;
			}
			if( reply.getReq("lastReceived") != null ) {
				if( !reply.getReq("lastReceived").equalsIgnoreCase("" + sender.getLastReceived()) )
					possible = false;
			}
			if( reply.getReq("name") != null ) {
				if( !reply.getReq("name").equalsIgnoreCase(sender.getName()) )
					possible = false;
			}
			if( reply.getReq("isTrue") != null ) {
				if( !scriptSystem.exec(reply.getReq("isTrue")).equalsIgnoreCase("true") )
					possible = false;
			}
			if( reply.getReq("isFalse") != null ) {
				if( !scriptSystem.exec(reply.getReq("isFalse")).equalsIgnoreCase("false") )
					possible = false;
			}
			if( reply.getReq("talkingTo") != null ) {
				if( !reply.getReq("talkingTo").equalsIgnoreCase(receiver.getName()) )
					possible = false;
			}
			
			if( possible ) { possibleReplies.add(reply); }
		}
		Collections.sort(possibleReplies);
		return possibleReplies;
	}
	
	public Dialog getTarget(Dialog dialog) { return dialogMapper.get( world.getEntity(dialog.getInteractionTarget()) ); }
	
	/**
	 * Starts necessary actions after a reply
	 * @param reply
	 */
	public void replyActions(Entity e, int reply) {
		if( replies.get(reply).getStopDialog() )
			stopDialog(e);
		if( replies.get(reply).getScript() != null ) {
			scriptSystem.exec(replies.get(reply).getScript());
		}
	}
	
	/**
	 * Starts a new dialog
	 * @param initiator Entity opening the dialog
	 * @param respondent Entity responding to the dialog
	 */
	public void startDialog(Entity initiator, Entity respondent) {
		if( dialogMapper.get(initiator) != null && dialogMapper.get(respondent) != null ) { // Sets the interaction target
			// Setting interaction targets
			dialogMapper.get(initiator).setInteractionTarget(respondent.getId());
			dialogMapper.get(respondent).setInteractionTarget(initiator.getId());
			
			// Setting states
			stateMapper.get(initiator).setState(C.STATES.TALKING);
			stateMapper.get(respondent).setState(C.STATES.TALKING);
			
			// Setting turn to speak
			dialogMapper.get(initiator).setCanSpeak(true);
			dialogMapper.get(respondent).setCanSpeak(false);
			
			// Test
			
		}
	}
	
	/**
	 * Terminates a dialog
	 * @param e the entity terminating the dialog
	 */
	public void stopDialog(Entity e) {
		Entity e2 = world.getEntity(dialogMapper.get(e).getInteractionTarget());
		// Setting interaction target
		dialogMapper.get(e).setInteractionTarget(-1);
		dialogMapper.get(e2).setInteractionTarget(-1);
		// Setting states
		stateMapper.get(e).setState(C.STATES.IDLE);
		stateMapper.get(e2).setState(C.STATES.IDLE);
		// Resetting last message
		dialogMapper.get(e).receiveMessage(0);
		dialogMapper.get(e2).receiveMessage(0);
		dialogGuiRemove();
	}
	
	
	/**
	 * Create dialog box
	 */
	public void dialogGuiSetup() {
		if( dialogBox == null ) {
			dialogBox = world.createEntity();
			dialogBox.setGroup("GUI");
			dialogBox.setTag("DialogBox");
			dialogBox.addComponent(new Transform(100, 200));
			dialogBox.addComponent(new SpatialForm(new DialogBox()));
			//dialogBox.getComponent(SpatialForm.class).getSpatial().setColor(new Color(0,0,255,100));
			dialogBox.refresh();
		}		
	}
	
	
	/**
	 * Remove the dialog box
	 */
	public void dialogGuiRemove() {
		if( dialogBox != null ) {
			dialogBox.delete();
			dialogBox = null;
			removeChoices();
		}			
	}
	
	/**
	 * Removing dialog choices for the player
	 */
	public void removeChoices() {
		for( int i=0; i<dialogEntities.size(); i++) {
			world.getEntity(dialogEntities.get(i).getId()).delete();
		}
		dialogEntities.clear();
	}

}
