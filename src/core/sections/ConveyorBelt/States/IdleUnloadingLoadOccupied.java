package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class IdleUnloadingLoadOccupied extends AutomataStateCB {

	private static final long serialVersionUID = 2597914525045171170L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AutomataStateCB estop() {
		return createState(OccupiedStop.class,this);
	}

	@Override
	public AutomataStateCB nstop() {
		return createState(OccupiedStop.class,this);
	}

	@Override
	public AutomataStateCB unloadSensorFalse() {
		return createState(Running.class,this);
	}


}
