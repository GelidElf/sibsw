package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class Trashing extends Slave3State {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5862049046925418350L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState EStop() {
		return  createState(StopTrashing.class,this);
	}
	
	@Override
	public AutomataState R2Idle() {
		return  createState(IdleQCSEmpty.class,this);
	}

}
