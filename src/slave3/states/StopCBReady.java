package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class StopCBReady extends Slave3State {
	
	private static final long serialVersionUID = 4118978392298535216L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState(CBReady.class,this);
	}

}
