package ConveyorBelt.States;

public class Idle implements AutomataStateCB{
	
static AutomataStateCB instance = null;
	
	public static AutomataStateCB getInstance(){
		if (instance == null){
			instance = new Idle();
		}
		return instance;
		
	}
	
	private Idle(){
		
	}
	

		@Override
		public AutomataStateCB estop() {
			// TODO Auto-generated method stub
			return Stop.getInstance();
		}

		@Override
		public AutomataStateCB restart() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public AutomataStateCB nstop() {
			// TODO Auto-generated method stub
			return Stop.getInstance();
		}

		@Override
		public AutomataStateCB loadSensorTrue() {
			// TODO Auto-generated method stub
			return Running.getInstance();
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

