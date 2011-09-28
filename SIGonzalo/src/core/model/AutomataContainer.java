package core.model;

import core.aplication.Configuration;
import core.messages.CommunicationManager;
import core.messages.Message;

public class AutomataContainer extends Thread {

	protected Configuration conf;
	protected AutomataState currentState;
	protected CommunicationManager commManager;
	
	public AutomataContainer (Configuration conf, CommunicationManager comm){
		this.conf = conf;
		currentState = null;
		this.commManager = comm;
	}
	
	public void injectMessage(Message message){
		commManager.getInbox().add(message);
	}
}
