package Slave.States;

import Slave.ATSlave1;

public class GearUnload implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		if(master.getRobot().isGearReady()==false){
			master.getAssemblyStation().setGearNeeded(false);
			master.setCurrentState(new LoadingAS());
		}
	}
}
