package core.sections.conveyorbelt2;

import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;

public class ConveyorBeltAutomata extends AutomataContainer<ConveyorBeltInput, ConveyorBeltState, ConveyorBeltModel> implements ParallelPortManagerObserver {

	public ConveyorBeltAutomata(AutomataContainer<?, ?, ?> father, ConveyorBeltModel model, CommunicationManager commManager) {
		super(father, model, commManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void consume(Message currentMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		// TODO Auto-generated method stub

	}

}
