package Slave.States;

public class GearLoading implements AutomataStateSlave{
	private static AutomataStateSlave instance;

	public static AutomataStateSlave getInstance(){
		if(instance == null){
			return new GearLoading();
		}else{
			return instance;
		}
	}

	private GearLoading(){
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
		return GearUnload.getInstance();
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
