package slave2.States;

import slave2.slave2Automata;

public class UnloadingAS implements AutomataStateSlave{

	@Override
	public void execute(slave2Automata master) {
		if(master.getAssemblyStation().isComplete()){
			master.getRobot().setUnloadAs(true);
			master.setCurrentState(new LoadingTransferCB());
		}
		
	}
}
