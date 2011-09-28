package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class StopQCSWorking extends Slave3State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2632623455555324952L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState("QCSWorking",this);
	}

}
