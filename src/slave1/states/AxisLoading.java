package slave1.states;

import slave1.ATSlave1;

public class AxisLoading implements AutomataStateSlave1{

	@Override
	public void execute(ATSlave1 master) {
		master.getRobot().setAxisReady(true);
		master.setCurrentState(new AxisUnload());
	}

	@Override
	public void execute(Slave.ATSlave1 master) {
		// TODO Auto-generated method stub
		
	}
	
}
