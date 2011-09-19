package Slave.States;

import Slave.ATSlave1;

public class GearUnload implements AutomataStateSlave{

	@Override
	public void execute(ATSlave1 master) {
		if(master.getRobot().isGearReady()==false){
			master.getAssemblyStation().setGearNeeded(false); //ya hemos llevado la pieza a la estación de montaje
			master.getGearBelt().piecePicked(); //notificamos al CB que ya hemos cogido la pieza
			master.setCurrentState(new LoadingAS());
		}
	}
}
