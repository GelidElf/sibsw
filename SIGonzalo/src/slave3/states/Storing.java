package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;
import slave3.ATslave3;

public class Storing extends Slave3State {
	
	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState EStop() {
		return  createState("StopStoring",this);
	}
	
	@Override
	public AutomataState NotFull() {
		return createState("CBReady", this);
	}
	
	@Override
	public AutomataState Full() {
		return createState("Storing", this);
	}

}