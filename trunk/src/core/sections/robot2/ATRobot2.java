package core.sections.robot2;

import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.robot2.states.Robot2State;

public class ATRobot2 extends Thread implements ParallelPortManagerObserver{
	
	private Robot2Manager _manager = null;
	private Robot2State currentState = null;
	private AutomataContainer father = null;
	
	@Override
	public void update(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}

}
