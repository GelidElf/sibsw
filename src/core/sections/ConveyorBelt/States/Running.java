package core.sections.ConveyorBelt.States;


public class Running extends AutomataStateCB{
	
	private static final long serialVersionUID = -8738809400931757119L;

	@Override
	public AutomataStateCB loadSensorTrue() {
		return createState(Running.class,this);
	}

	@Override
	public AutomataStateCB unloadSensorTrue() {
		return createState(IdleUnloading.class,this);
	}

	@Override
	public AutomataStateCB empty() {
		return createState(Idle.class,this);
	}

	@Override
	public AutomataStateCB unloadSensorTrueMax() {
		return createState(Full.class,this);
	}

}

