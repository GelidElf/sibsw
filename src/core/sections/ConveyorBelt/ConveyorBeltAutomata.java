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
	private Enum<?> canAcceptElements;

	public ConveyorBeltAutomata(String name, AutomataContainer<?, ?, ?> father, ConveyorBeltManager manager, Enum<?> jobDone, Enum<?> canAcceptElements) {
		super(father, new ConveyorBeltModel(), new OfflineCommunicationManager());
		this.setName(name+"CBAutomata");
		getModel().setAutomata(this);
		this.manager = manager;
		manager.registerObserver(this);
		simulator = new ConveyorBeltSimulator(name,this.manager);
		Logger.println("AT CB Created");
		this.jobDone = jobDone;
		this.canAcceptElements = canAcceptElements;
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
			ConveyorBeltInput input = (ConveyorBeltInput) message.getInputType();
			if (input == ConveyorBeltInput.loadSensorTrue){
				try {
					manager.setValueByNameAsBoolean(ConveyorBeltManager.SENSOR_LOAD, true);
				} catch (ParallelPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				boolean stateChanged = reactToInput(input);
				message.setConsumed(stateChanged);
			}
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
		simulator.setAutoFeeed(true);
	}

	public void disableAutoFeed() {
		simulator.setAutoFeeed(false);
	}

	public Enum<?> getJobDoneInput() {
		return jobDone;
	}

	public Enum<?> getCanAcceptElements() {
		return canAcceptElements;
	}

}
