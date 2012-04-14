package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class StopTrashing extends Slave3State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583687963439647377L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState(Trashing.class,this);
	}
	

}
