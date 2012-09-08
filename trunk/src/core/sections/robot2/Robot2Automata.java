package core.sections.robot2;

import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;


public class Robot2Automata extends AutomataContainer<Robot2Input, core.sections.robot2.Robot2State, Robot2Model> implements ParallelPortManagerObserver {

	private Robot2Simulator simulator;
	private Robot2Manager manager;

	public Robot2Automata(AutomataContainer<?, ?, ?> father, Robot2Model model, CommunicationManager commManager) {
		super(father, model, commManager);
		manager = new Robot2Manager();
		manager.configure(7, 7, 7);
		manager.registerObserver(this);
		simulator = new Robot2Simulator(manager);
		getModel().setAutomata(this);
		//		getModel().addListener(this);
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		/*try {
			if (attribute.getName().equals("ROBOT1SPEED")) {
				manager.setValueByName(Robot2Manager.TIME_TO_AXIS_GEAR, (Integer) attribute.getValue());
			}
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}*/

	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			reactToInput((Robot2Input) message.getInputType());
		}
	}

	private void reactToInput(Robot2Input input) {
		getModel().getState().execute(input);
	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		switch (message.getType()) {
		case START:
			reactToInput(Robot2Input.START);
			break;
		case NSTOP:
			reactToInput(Robot2Input.NSTOP);
			break;
		case ESTOP:
			reactToInput(Robot2Input.ESTOP);
			break;
		case RESTART:
			reactToInput(Robot2Input.RESTART);
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
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
		if (manager.getModifiedGroupName().equals(Robot2Manager.ENABLE) && !((Robot2Manager) manager).jobToBeDone()) {
			feedInput(Robot2Input.JobDone, false);
		}

	}

	public ParallelPortManager getManager() {
		return manager;
	}

}
