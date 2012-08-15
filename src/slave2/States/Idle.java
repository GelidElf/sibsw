package slave2.States;

import slave2.slave2Automata;

public class Idle implements AutomataStateSlave{

	@Override
	public void execute(slave2Automata master) {
		if(master.getAssemblyStation().isComplete()){
			master.setCurrentState(new UnloadingAS());
		}else if(master.getAssemblyStation().isEmpty()){
			master.setCurrentState(new LoadingAS());
		}
		
	}
}
