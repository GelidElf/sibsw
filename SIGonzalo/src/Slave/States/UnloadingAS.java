package Slave.States;

import Slave.ATSlave1;

public class UnloadingAS implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		if(master.isASUnloadingStationReady()){
			master.getRobot().unloadAS(true);
			master.setCurrentState(new LoadingTransferCB());
		}
		
	}
}
