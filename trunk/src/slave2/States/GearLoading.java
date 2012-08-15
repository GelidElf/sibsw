package slave2.States;

import slave2.slave2Automata;

public class GearLoading implements AutomataStateSlave{

	@Override
	public void execute(slave2Automata master) {
		master.getRobot().setGearReady(true);
		master.setCurrentState(new GearUnload());
		
	}
}
