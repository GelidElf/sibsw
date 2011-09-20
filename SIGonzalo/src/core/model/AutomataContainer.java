package core.model;

import core.aplication.Configuration;

public class AutomataContainer extends Thread {

	protected Configuration conf;
	protected AutomataState currentState;
	
	public AutomataContainer (Configuration conf){
		this.conf = conf;
		currentState = null;
	}
	
}
