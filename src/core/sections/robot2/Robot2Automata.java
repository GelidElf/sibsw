package core.sections.robot2;

import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;

public class Robot2Automata extends AutomataContainer<Robot2Input, core.sections.robot2.Robot2State, Robot2Model> implements ParallelPortManagerObserver {

	public Robot2Automata(AutomataContainer<?, ?, ?> father) {
		super(father, new Robot2Model(), new OfflineCommunicationManager());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startCommand() {
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