package Slave.States;

import Slave.ATSlave1;

public class GearLoading implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		master.getRobot().setGearReady(true);
		master.setCurrentState(new GearUnload());
		
	}
}
