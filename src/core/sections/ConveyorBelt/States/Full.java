package core.sections.ConveyorBelt.States;

import core.model.AutomataContainer;

public class Full extends AutomataStateCB{
	
	private static final long serialVersionUID = 5060457061159062453L;

	@Override
	public void execute(AutomataContainer master) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public AutomataStateCB estop() {
		return createState(StopFull.class,this);
	}

	@Override
	public AutomataStateCB nstop() {
		return createState(StopFull.class,this);
	}
	@Override
	public AutomataStateCB unloadSensorTrue() {
		return createState(IdleUnloading.class,this);
	}



}
