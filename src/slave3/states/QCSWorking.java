package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class QCSWorking extends Slave3State {
	private static final long serialVersionUID = 3620690225049452264L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState EStop() {
		return createState("StopQCSWorking",this);
	}
	
	@Override
	public AutomataState Valid() {
		return createState("Storing",this);
	}
	
	@Override
	public AutomataState Invalid() {
		return createState("Trashing",this);
	}

}
