package slave1.states;

import slave1.ATSlave1;

public class UnloadingAS implements AutomataStateSlave1{

	@Override
	public void execute(ATSlave1 master) {
		if(master.getAssemblyStation().isComplete()){
			master.getRobot().setUnloadAs(true);
			master.setCurrentState(new LoadingTransferCB());
		}
		
	}
}
