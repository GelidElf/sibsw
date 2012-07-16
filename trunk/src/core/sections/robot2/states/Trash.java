package core.sections.robot2.states;

import core.model.AutomataState;

public class Trash extends Robot2State{
	
	private static final long serialVersionUID = -2584948085473623710L;

	@Override
	public AutomataState EStop(){
		return createState(StopTrash.class,this);
	}
	
	@Override
	public AutomataState Completed(){
		return createState(Idle.class,this);
	}

}
