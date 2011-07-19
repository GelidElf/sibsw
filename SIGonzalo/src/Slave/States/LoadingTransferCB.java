package Slave.States;

public class LoadingTransferCB implements AutomataStateSlave{
	private static AutomataStateSlave instance;

	public static AutomataStateSlave getInstance(){
		if(instance == null){
			return new LoadingTransferCB();
		}else{
			return instance;
		}
	}

	private LoadingTransferCB(){
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
		return ASUnload.getInstance();
	}

	@Override
	public AutomataStateSlave robot1Loaded(){
		// TODO Auto-generated method stub
		return null;
	}
}
