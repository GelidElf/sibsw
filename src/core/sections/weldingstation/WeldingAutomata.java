package core.sections.weldingstation;

import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class WeldingAutomata extends AutomataContainer<WeldingInput, WeldingState, WeldingModel> implements ParallelPortManagerObserver {

	private WeldingSimulator simulator;
	private WeldingManager manager;

	public WeldingAutomata(AutomataContainer<?, ?, ?> father, WeldingModel model, CommunicationManager commManager) {
		super(father, model, commManager);
		this.setName("WSAutomata");
		manager = new WeldingManager();
		manager.configure(7, 7, 7, 7);
		manager.registerObserver(this);
		simulator = new WeldingSimulator(manager);
		getModel().setAutomata(this);
		//		getModel().addListener(this);
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			if (attribute.getName().equals("WELDINGTIME")) {
				manager.setValueByName(WeldingManager.TIME_TO_WELD, (Integer) attribute.getValue());
			}
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			message.setConsumed(reactToInput((WeldingInput) message.getInputType()));
		}
	}

	private boolean reactToInput(WeldingInput input) {
		return getModel().getState().execute(input);
	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		switch (message.getType()) {
		case START:
			message.setConsumed(reactToInput(WeldingInput.START));
			break;
		case NSTOP:
			message.setConsumed(reactToInput(WeldingInput.NSTOP));
			break;
		case ESTOP:
			message.setConsumed(reactToInput(WeldingInput.ESTOP));
			break;
		case RESTART:
			message.setConsumed(reactToInput(WeldingInput.RESTART));
			break;
		case CONFIGURATION:
			for (Attribute attribute : message.getAttributes()) {
				changeConfigurationParameter(attribute);
			}
			message.consumeMessage();
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
		if (manager.getModifiedGroupName().equals(WeldingManager.ENABLE) && !((WeldingManager) manager).jobToBeDone()) {
			feedInput(WeldingInput.JobDone, false);
		}

	}

	public WeldingManager getManager() {
		return manager;
	}

}
