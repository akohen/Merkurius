package fr.kohen.alexandre.wip.masTest.mas;

import java.util.List;

import com.artemis.Entity;

public interface AgentSystem {

	public AgentAddress getAgentAddress(Entity e);
	public List<Entity> getEntitiesFromAddress(AgentAddress address);
	
	public void sendMessage(Message message, AgentAddress address);
	
	/**
	 * @return new unique conversation id
	 */
	public int getNewConversationId();
}
