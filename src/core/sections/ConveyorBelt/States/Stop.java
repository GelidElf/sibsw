package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class Stop extends AutomataStateCB {

	private static final long serialVersionUID = 4853069318562690881L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AutomataStateCB restart() {
		return createState(Running.class, this);
	}

}
