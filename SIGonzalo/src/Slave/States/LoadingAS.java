package Slave.States;

import Slave.ATSlave1;

public class LoadingAS implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		if(master.getAxisBelt().isReady() && master.getAssemblyStation().isAxisNeeded()){
			System.out.println("Slave1: axis loading...");
			master.setCurrentState(new AxisLoading());
		}else if(master.getGearBelt().isReady() && master.getAssemblyStation().isGearNeeded()){
			System.out.println("Slave1: gear loading...");
			master.setCurrentState(new GearLoading());
		}else{
			System.out.println("Slave1: Waiting for gears or axis...");
		}
		
	}
}
