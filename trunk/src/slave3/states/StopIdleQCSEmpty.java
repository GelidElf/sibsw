package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class StopIdleQCSEmpty extends Slave3State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6996856352516262850L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState Restart() {
		return createState(IdleQCSEmpty.class,this);
	}

}
