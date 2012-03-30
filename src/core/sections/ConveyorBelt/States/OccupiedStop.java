package core.sections.ConveyorBelt.States;

public class OccupiedStop implements AutomataStateCB{
static AutomataStateCB instance = null;
	
	public static AutomataStateCB getInstance(){
		if (instance == null){
			instance = new OccupiedStop();
		}
		return instance;
		
	}
	
	private OccupiedStop(){
		
	}
	

	@Override
	public AutomataStateCB estop() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public AutomataStateCB restart() {
		// TODO Auto-generated method stub
		return IdleUnloadingLoadOccupied.getInstance();
	}

	@Override
	public AutomataStateCB nstop() {
		// TODO Auto-generated method stub
		return this;
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
		return this;
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

