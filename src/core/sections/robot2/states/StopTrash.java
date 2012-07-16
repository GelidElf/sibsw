package core.sections.robot2.states;

import core.model.AutomataState;

public class StopTrash extends Robot2State{
	
	private static final long serialVersionUID = -2802183425127847608L;

	@Override
	public AutomataState Restart(){
		return createState(Trash.class,this);
	}


}
