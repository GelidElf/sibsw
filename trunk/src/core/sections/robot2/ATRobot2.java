package core.sections.robot2;

import core.messages.Attribute;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;

public class ATRobot2 extends AutomataContainer<ATRobot2Input> implements ParallelPortManagerObserver{

	public ATRobot2(AutomataContainer<?> father) {
		super(father);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void consume(ATRobot2Input currentInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub
		
	}
	
}
