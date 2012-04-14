package core.model;

import core.aplication.Configuration;
import core.messages.CommunicationManager;
import core.messages.Message;

public class AutomataContainer {

	protected Configuration conf;
	protected AutomataState currentState;
	protected CommunicationManager commManager;
	protected AutomataContainer father;
	
	public AutomataContainer (Configuration conf, CommunicationManager comm){
		this.conf = conf;
		currentState = null;
		this.commManager = comm;
		father = null;
	}
	
	public AutomataContainer(AutomataContainer father){
		this.father = father;
	}
	
	public void injectMessage(Message message){
		commManager.getInbox().add(message);
	}
}
