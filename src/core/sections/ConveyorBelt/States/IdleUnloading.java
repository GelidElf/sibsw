package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class IdleUnloading extends AutomataStateCB{
	
	private static final long serialVersionUID = 7438229229496238541L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AutomataStateCB estop() {
		return createState(UnloadStop.class,this);
	}

	@Override
	public AutomataStateCB nstop() {
		return createState(UnloadStop.class,this);
	}

	@Override
	public AutomataStateCB loadSensorTrue() {
		return createState(IdleUnloadingLoadOccupied.class,this);
	}

	@Override
	public AutomataStateCB unloadSensorFalse() {
		return createState(Running.class,this);
	}


}

