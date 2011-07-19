package Slave.States;

public class AxisUnload implements AutomataStateSlave{
	private static AutomataStateSlave instance;

	public static AutomataStateSlave getInstance(){
		if(instance == null){
			return new AxisUnload();
		}else{
			return instance;
		}
	}

	private AxisUnload(){
	}

	@Override
	public AutomataStateSlave asEmpty(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomataStateSlave asComplete(){
		// TODO Auto-generated method stub
		return null;
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
		return LoadingAS.getInstance();
	}
}
