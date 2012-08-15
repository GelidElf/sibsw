package slave2.States;

import slave2.slave2Automata;

public class GearUnload implements AutomataStateSlave{

	@Override
	public void execute(slave2Automata master) {
		if(master.getRobot().isGearReady()==false){
			master.getAssemblyStation().setGearNeeded(false); //ya hemos llevado la pieza a la estación de montaje
			master.getGearBelt().piecePicked(); //notificamos al CB que ya hemos cogido la pieza
			master.setCurrentState(new Idle());
		}
	}
}
