package slave2.States;

import slave2.slave2Automata;
import core.utilities.log.Logger;

public class LoadingAS implements AutomataStateSlave {

	@Override
	public void execute(slave2Automata master) {
		if (master.getAxisBelt().isReady() && master.getAssemblyStation().isAxisNeeded()) {
			Logger.println("Slave1: axis loading...");
			master.setCurrentState(new AxisLoading());
		} else if (master.getGearBelt().isReady() && master.getAssemblyStation().isGearNeeded()) {
			Logger.println("Slave1: gear loading...");
			master.setCurrentState(new GearLoading());
		} else {
			Logger.println("Slave1: Waiting for gears or axis...");
		}

	}
}
