package slave2.States;

import slave2.slave2Automata;
import core.utilities.log.Logger;

public class LoadingTransferCB implements AutomataStateSlave {

	@Override
	public void execute(slave2Automata master) {
		Logger.println("Assembled piece into transfer CB...");
		master.getRobot().setUnloadAs(false);
		master.getAssemblyStation().setComplete(false);
		master.getAssemblyStation().setAxisNeeded(true);
		master.getAssemblyStation().setGearNeeded(true);
		master.getAssemblyStation().setEmpty(true);
		master.setCurrentState(new Idle());

	}
}
