package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class IdleQCSEmpty extends Slave3State {
	private static final long serialVersionUID = 6497044596704079337L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
	}

	@Override
	public AutomataState EStop() {
		return createState("StopIdleQCSEmpty",this);
	}

	@Override
	public AutomataState LoadQCS() {
		return createState("QCSWorking",this);
	}


}
