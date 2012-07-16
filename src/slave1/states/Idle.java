package slave1.states;

import slave1.ATSlave1;

public class Idle implements AutomataStateSlave1{

	@Override
	public void execute(ATSlave1 master) {
		if(master.getAssemblyStation().isComplete()){
			master.setCurrentState(new UnloadingAS());
		}else if(master.getAssemblyStation().isEmpty()){
			master.setCurrentState(new LoadingAS());
		}
		
	}
}
