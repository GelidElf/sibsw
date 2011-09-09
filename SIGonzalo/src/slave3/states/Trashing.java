package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;
import slave3.ATslave3;

public class Trashing extends Slave3State {


	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub

	}

	@Override
	public AutomataState EStop() {
		return  createState("StopTrashing",this);
	}
	
	@Override
	public AutomataState R2Idle() {
		return  createState("IdleQCSEmpty",this);
	}

}
