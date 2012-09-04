package core.sections.QualityStation;

import core.messages.Attribute;
import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class QualityStationAutomata extends AutomataContainer<QualityStationInput, QualityStationState, QualityStationModel> implements ParallelPortManagerObserver {

	private QualityStationSimulator simulator;
	private QualityStationManager manager;

	public QualityStationAutomata(AutomataContainer<?, ?, ?> father, QualityStationModel model, CommunicationManager commManager) {
		super(father, model, commManager);
		manager = new QualityStationManager();
		manager.configure(5);
		manager.registerObserver(this);
		simulator = new QualityStationSimulator(manager);
		getModel().setAutomata(this);
		//		getModel().addListener(this);
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			if (attribute.getName().equals(QualityStationManager.ACTIVATION_TIME)) {
				manager.setValueByName(QualityStationManager.ACTIVATION_TIME, (Integer) attribute.getValue());
			}
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			reactToInput((QualityStationInput) message.getInputType());
		}
	}

	private void reactToInput(QualityStationInput input) {
		getModel().getState().execute(input);
	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		switch (message.getType()) {
		case START:
			reactToInput(QualityStationInput.START);
			break;
		case NSTOP:
			reactToInput(QualityStationInput.NSTOP);
			break;
		case ESTOP:
			reactToInput(QualityStationInput.ESTOP);
			break;
		case RESTART:
			reactToInput(QualityStationInput.RESTART);
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
		if (manager.getModifiedGroupName().equals(QualityStationManager.ENABLED) && !((QualityStationManager) manager).jobToBeDone()) {
			try {
				boolean resultadoOk = manager.getValueByNameAsBoolean(QualityStationManager.RESULT);
				if (resultadoOk) {
					feedInput(QualityStationInput.JobDoneOK, false);
				} else {
					feedInput(QualityStationInput.JobDoneKO, false);
				}
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}

	}

	public ParallelPortManager getManager() {
		return manager;
	}

}
