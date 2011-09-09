package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;
import slave3.ATslave3;

public class StopQCSWorking extends Slave3State {
	
	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState("QCSWorking",this);
	}

}
