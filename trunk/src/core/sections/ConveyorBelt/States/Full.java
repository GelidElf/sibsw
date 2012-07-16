package core.sections.ConveyorBelt.States;


public class Full extends AutomataStateCB{
	
	private static final long serialVersionUID = 5060457061159062453L;

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
