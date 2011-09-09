package slave3.states;

import core.model.AutomataState;

public abstract class Slave3State extends AutomataState {
	
	public AutomataState Start(){
		return this;
	}
	public AutomataState EStop(){
		return this;
	}
	public AutomataState NStop(){
		return this;
	}
	public AutomataState R2Idle(){
		return this;
	}
	public AutomataState LoadQCS(){
		return this;
	}
	public AutomataState Invalid(){
		return this;
	}
	public AutomataState Valid(){
		return this;
	}
	public AutomataState NotFull(){
		return this;
	}
	public AutomataState Full(){
		return this;
	}
	public AutomataState Restart(){
		return this;
	}
	
}
