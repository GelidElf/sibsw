package core.sections.robot2.states;

import core.model.AutomataState;

public class Ok extends Robot2State{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public AutomataState Completed(){
		return createState(Idle.class,this);
	}
	
	@Override
	public AutomataState EStop(){
		return createState(StopOK.class,this);
	}

}
