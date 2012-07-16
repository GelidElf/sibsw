package slave1.states;

import slave1.ATSlave1;

public class GearLoading implements AutomataStateSlave1{

	@Override
	public void execute(ATSlave1 master) {
		master.getRobot().setGearReady(true);
		master.setCurrentState(new GearUnload());
		
	}
}
