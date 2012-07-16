package core.sections.robot2.states;

import core.model.AutomataState;

public class StopWS extends Robot2State{
	
	private static final long serialVersionUID = 3088389519763969957L;

	@Override
	public AutomataState Restart(){
		return createState(WS.class,this);
	}

}
