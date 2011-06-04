package ConveyorBelt.States;

public class IdleUnloadingLoadOccupied implements AutomataStateCB{
static AutomataStateCB instance = null;
	
	public static AutomataStateCB getInstance(){
		if (instance == null){
			instance = new IdleUnloadingLoadOccupied();
		}
		return instance;
		
	}
	
	private IdleUnloadingLoadOccupied(){
		
	}
	

	@Override
	public AutomataStateCB estop() {
		// TODO Auto-generated method stub
		return OccupiedStop.getInstance();
	}

	@Override
	public AutomataStateCB restart() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public AutomataStateCB nstop() {
		// TODO Auto-generated method stub
		return OccupiedStop.getInstance();
	}

	@Override
	public AutomataStateCB loadSensorTrue() {
		// TODO Auto-generated method stub
		return this;
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

