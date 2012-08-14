package core.sections.robot2;

import core.messages.Attribute;
import core.messages.Message;
import core.model.AutomataContainer;
import core.model.DummyAutomataModel;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;

public class ATRobot2 extends AutomataContainer<ATRobot2Input> implements ParallelPortManagerObserver{

	public ATRobot2(AutomataContainer<?> father) {
		super(father, new DummyAutomataModel());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void startCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void consume(Message currentMessage) {
		// TODO Auto-generated method stub
		
	}
	
}
