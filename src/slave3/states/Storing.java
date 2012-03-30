package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class Storing extends Slave3State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4665809406537913468L;

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