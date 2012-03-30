package core.sections.ConveyorBelt.States;

public class IdleUnloading implements AutomataStateCB{
	
static AutomataStateCB instance = null;
	
	public static AutomataStateCB getInstance(){
		if (instance == null){
			instance = new IdleUnloading();
		}
		return instance;
		
	}
	
	private IdleUnloading(){
		
	}
	

	@Override
	public AutomataStateCB estop() {
		// TODO Auto-generated method stub
		return UnloadStop.getInstance();
	}

	@Override
	public AutomataStateCB restart() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public AutomataStateCB nstop() {
		// TODO Auto-generated method stub
		return UnloadStop.getInstance();
	}

	@Override
	public AutomataStateCB loadSensorTrue() {
		// TODO Auto-generated method stub
		return IdleUnloadingLoadOccupied.getInstance();
	}

	@Override
	public AutomataStateCB unloadSensorTrue() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public AutomataStateCB unloadSensorFalse() {
		// TODO Auto-generated method stub
		return Running.getInstance();
	}

	@Override
	public AutomataStateCB empty() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public AutomataStateCB unloadSensorTrueMax() {
		// TODO Auto-generated method stub
		return this;
	}

}

