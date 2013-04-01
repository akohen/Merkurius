package fr.kohen.alexandre.wip.masTest.mas;

import com.artemis.Component;

public class AgentComponent extends Component {
	private Agent agent;
	
	public AgentComponent(Agent agent) { this.agent = agent; }

	public void process() { agent.process(); }
	public void initialize() { agent.initialize(); }
}
