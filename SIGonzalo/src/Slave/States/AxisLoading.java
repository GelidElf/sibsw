package Slave.States;

import Slave.ATSlave1;

public class AxisLoading implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		master.getRobot().setAxisReady(true);
		master.setCurrentState(new AxisUnload());
	}
	
}
