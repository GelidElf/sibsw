package ConveyorBelt.States;

public interface AutomataStateCB {

	public abstract AutomataStateCB estop();
	public abstract AutomataStateCB restart();
	public abstract AutomataStateCB nstop();
	public abstract AutomataStateCB loadSensorTrue();
	public abstract AutomataStateCB unloadSensorTrue();
	public abstract AutomataStateCB unloadSensorTrueMax();
	public abstract AutomataStateCB unloadSensorFalse();
	public abstract AutomataStateCB empty();
	
}
