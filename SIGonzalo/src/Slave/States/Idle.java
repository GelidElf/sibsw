package Slave.States;

public class Idle implements AutomataStateSlave{
	private static AutomataStateSlave instance;

	public static AutomataStateSlave getInstance(){
		if(instance == null){
			return new Idle();
		}else{
			return instance;
		}
	}

	private Idle(){
	}

	@Override
	public AutomataStateSlave asEmpty(){
		return LoadingAS.getInstance();
	}

	@Override
	public AutomataStateSlave asComplete(){
		return UnloadingAS.getInstance();
	}

	@Override
	public AutomataStateSlave transferCBNotFull(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave asAxisNeeded(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave axisCBUnloadReady(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave asGearNeeded(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave gearCBUnloadReady(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave robot1Idle(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave robot1Loaded(){
		// TODO Auto-generated method stub
		return null;
	}
}
