package slave1.states;

import slave1.ATSlave1;

public class AxisUnload implements AutomataStateSlave1{

	@Override
	public void execute(ATSlave1 master) {
		if(master.getRobot().isAxisReady()==false){
			master.getAssemblyStation().setAxisNeeded(false); //ya hemos llevado la pieza a la estación de montaje
			master.getAxisBelt().piecePicked(); //notificamos al CB que ya hemos cogido la pieza
			master.setCurrentState(new Idle());
		}
		
	}
}
