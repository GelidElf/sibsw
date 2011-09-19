package Slave.States;

import Slave.ATSlave1;

public class LoadingTransferCB implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		System.out.println("Assembled piece into transfer CB...");
		master.getRobot().setUnloadAs(false);
		master.getAssemblyStation().setComplete(false);
		master.getAssemblyStation().setAxisNeeded(true);
		master.getAssemblyStation().setGearNeeded(true);
		master.getAssemblyStation().setEmpty(true);
		master.setCurrentState(new Idle());
		
	}
}
