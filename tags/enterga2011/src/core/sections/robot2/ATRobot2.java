package core.sections.robot2;

import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.robot2.states.Robot2State;

public class ATRobot2 extends Thread implements ParallelPortObserver{
	
	private Robot2Manager _manager = null;
	private Robot2State currentState = null;
	private AutomataContainer father = null;

	@Override
	public void update(ParallelPortState state) {
		_manager.setState(state);		
	}

	@Override
	public void setParallelPortState(ParallelPortState state) {
		// TODO Auto-generated method stub
		
	}

}
