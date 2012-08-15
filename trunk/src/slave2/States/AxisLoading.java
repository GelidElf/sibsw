package slave2.States;

import slave2.slave2Automata;

public class AxisLoading implements AutomataStateSlave{

	@Override
	public void execute(slave2Automata master) {
		master.getRobot().setAxisReady(true);
		master.setCurrentState(new AxisUnload());
	}
	
}
