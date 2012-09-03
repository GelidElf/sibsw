package core.sections.robot1;

import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class Robot1Automata extends AutomataContainer<Robot1Input, Robot1State, Robot1Model> implements ParallelPortManagerObserver {

	private Robot1Simulator simulator;
	private Robot1Manager manager;

	public Robot1Automata(AutomataContainer<?, ?, ?> father, Robot1Model model, CommunicationManager commManager) {
		super(father, model, commManager);
		manager = new Robot1Manager();
		manager.configure(7, 7, 7);
		manager.registerObserver(this);
		simulator = new Robot1Simulator(manager);
		getModel().setAutomata(this);
		//		getModel().addListener(this);
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			if (attribute.getName().equals("ROBOT1SPEED")) {
				manager.setValueByName(Robot1Manager.TIME_TO_AXIS_GEAR, (Integer) attribute.getValue());
			}
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			reactToInput((Robot1Input) message.getInputType());
		}
	}

	private void reactToInput(Robot1Input input) {
		getModel().getState().execute(input);
	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		message.consumeMessage();
		switch (message.getType()) {
		case START:
			reactToInput(Robot1Input.START);
			break;
		case NSTOP:
			reactToInput(Robot1Input.NSTOP);
			break;
		case ESTOP:
			reactToInput(Robot1Input.ESTOP);
			break;
		case RESTART:
			reactToInput(Robot1Input.RESTART);
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
			break;
		default:
			message.didNotConsumeMessage();
			break;
		}
	}

	@Override
	public void startCommand() {
		simulator.start();
		this.start();
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (manager.getModifiedGroupName().equals(Robot1Manager.ENABLE) && !((Robot1Manager) manager).jobToBeDone()) {
			feedInput(Robot1Input.JobDone, false);
		}

	}

}
