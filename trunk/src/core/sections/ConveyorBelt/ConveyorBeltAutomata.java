package core.sections.ConveyorBelt;

import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

public class ConveyorBeltAutomata extends AutomataContainer<ConveyorBeltInput, ConveyorBeltState, ConveyorBeltModel> implements ParallelPortManagerObserver {

	private ConveyorBeltManager manager = null;
	private ConveyorBeltSimulator simulator;
	private Enum<?> jobDone;
	private ConveyorBeltRandomFiller fillerThread = null;

	public ConveyorBeltAutomata(AutomataContainer<?, ?, ?> father, ConveyorBeltManager manager, Enum<?> jobDone) {
		super(father, new ConveyorBeltModel(), new OfflineCommunicationManager());
		this.setName("ConveyorBeltAutomata");
		getModel().setAutomata(this);
		this.manager = manager;
		manager.registerObserver(this);
		simulator = new ConveyorBeltSimulator(this.manager);
		Logger.println("AT CB Created");
		fillerThread = new ConveyorBeltRandomFiller(manager);
		this.jobDone = jobDone;
	}

	public void setupMockParametersFromMessage() {
		int speed = 2;
		int capacity = 10;
		try {
			manager.setValueByName(ConveyorBeltManager.SPEED, speed);
			manager.setValueByName(ConveyorBeltManager.CAPACITY, capacity);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			e1.printStackTrace();
		}
	}

	public void piecePicked() {
		manager.setSensorFinish(false);
	}

	// Method to check if the piece is ready to pick up
	public boolean isReady() {
		return manager.isSensorFinish();
	}

	// Method to check if the first position of the CB is empty
	public boolean isFirstPositionEmpty() {
		return manager.isSensorInitial();
	}

	public ConveyorBeltManager getManager() {
		return manager;
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (this.manager.getModifiedGroupName().equals(ConveyorBeltManager.SENSOR_UNLOAD)){
			if (this.manager.isSensorFinish()) {
				feedInput(ConveyorBeltInput.unloadSensorTrue, false);
			}
		}
	}


	@Override
	protected void consume(Message message) {
		reaccionaPorTipoDeMensaje(message);
		if (debeReaccionaPorTipoEntrada(message)) {
			boolean stateChanged = reactToInput((ConveyorBeltInput) message.getInputType());
			message.setConsumed(stateChanged);
		}
	}

	private boolean reactToInput(ConveyorBeltInput input) {
		return getModel().getState().execute(input);
	}

	private boolean debeReaccionaPorTipoEntrada(Message message) {
		return message.getType() == CommunicationMessageType.COMMAND;
	}

	private void reaccionaPorTipoDeMensaje(Message message) {
		message.consumeMessage();
		switch (message.getType()) {
		case START:
			reactToInput(ConveyorBeltInput.START);
			break;
		case NSTOP:
			reactToInput(ConveyorBeltInput.NSTOP);
			break;
		case ESTOP:
			reactToInput(ConveyorBeltInput.ESTOP);
			break;
		case RESTART:
			reactToInput(ConveyorBeltInput.RESTART);
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
		fillerThread.start();
		this.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			manager.setValueByName(attribute.getName(), Integer.parseInt((String) attribute.getValue()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}

	}

	public void enableAutoFeed(){
		fillerThread.setEnabled(true);
	}

	public void disableAutoFeed() {
		fillerThread.setEnabled(false);
	}

	public Enum<?> getJobDoneInput() {
		return jobDone;
	}

	public void setLength(int length) {
		// size of piece is 0.1 m, length comes in meters
		try {
			int value = (int) (length / 0.1);
			manager.setValueByName(ConveyorBeltManager.CAPACITY, value);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
