package core.sections.robot2.states;

import core.model.AutomataState;

public class Stop extends Robot2State{
	
	private static final long serialVersionUID = -6829175966215531344L;

	@Override
	public AutomataState Restart(){
		return createState(Idle.class,this);
	}


}
