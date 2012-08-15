package slave2.States;

import slave2.slave2Automata;

public class AxisUnload implements AutomataStateSlave{

	@Override
	public void execute(slave2Automata master) {
		if(master.getRobot().isAxisReady()==false){
			master.getAssemblyStation().setAxisNeeded(false); //ya hemos llevado la pieza a la estación de montaje
			master.getAxisBelt().piecePicked(); //notificamos al CB que ya hemos cogido la pieza
			master.setCurrentState(new Idle());
		}
		
	}
}
