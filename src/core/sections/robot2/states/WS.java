package core.sections.robot2.states;

import core.model.AutomataState;

public class WS extends Robot2State{

	private static final long serialVersionUID = -3747631783744535278L;

	@Override
	public AutomataState Completed(){
		return createState(Idle.class,this);
	}
	
	@Override
	public AutomataState EStop(){
		return createState(StopWS.class,this);
	}
	
	
	
	
}
