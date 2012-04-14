package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class UnloadStop extends AutomataStateCB{
	
	private static final long serialVersionUID = 1604310742418559089L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AutomataStateCB restart() {
		return createState(IdleUnloading.class,this);
	}


}

