package fr.kohen.alexandre.wip.masTest;

import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import fr.kohen.alexandre.wip.masTest.mas.AgentAddress;
import fr.kohen.alexandre.wip.masTest.mas.AgentComponent;
import fr.kohen.alexandre.wip.masTest.mas.AgentSystem;
import fr.kohen.alexandre.wip.masTest.mas.Message;

public class MasTestSystem extends EntityProcessingSystem implements AgentSystem {
	protected ComponentMapper<AgentComponent> agentMapper;
	private int conversationId = 0;
	
	@SuppressWarnings("unchecked")
	public MasTestSystem() {
		super(Aspect.getAspectForAll(AgentComponent.class));
	}
	
	@Override
	protected void initialize() {
		agentMapper = ComponentMapper.getFor(AgentComponent.class, world);
	}


	protected void inserted(Entity e) {
		agentMapper.get(e).initialize();
	}
	
	@Override
	protected void process(Entity e) {
		agentMapper.get(e).process();
	}

	@Override
	public AgentAddress getAgentAddress(Entity e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getEntitiesFromAddress(AgentAddress address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message message, AgentAddress address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNewConversationId() { return ++conversationId; }

}
