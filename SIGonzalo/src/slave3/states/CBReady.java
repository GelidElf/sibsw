package slave3.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class CBReady extends Slave3State {
	private static final long serialVersionUID = -8465714678878030143L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public AutomataState EStop(){
		return createState("StopCBReady",this);
	}
	
	@Override
	public AutomataState R2Idle(){
		return createState("IdleQCSEmpty",this);
	}

}
