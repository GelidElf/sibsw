package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class StopFull extends AutomataStateCB{
	
	private static final long serialVersionUID = -9099799405325292141L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AutomataStateCB restart() {
		return createState(Full.class,this);
	}


}

