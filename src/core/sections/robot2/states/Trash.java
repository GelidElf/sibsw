package core.sections.robot2.states;

import core.model.AutomataContainer;
import core.model.AutomataState;

public class Trash extends Robot2State{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public AutomataState EStop(){
		return createState("StopTrash",this);
	}
	
	@Override
	public AutomataState Completed(){
		return createState("Idle",this);
	}

}
