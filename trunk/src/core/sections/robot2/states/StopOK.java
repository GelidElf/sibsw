package core.sections.robot2.states;

import core.model.AutomataState;

public class StopOK extends Robot2State{
	
	private static final long serialVersionUID = -2420752385555738157L;

	@Override
	public AutomataState Restart(){
		return createState(Ok.class,this);
	}
	


}
