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

	private ConveyorBeltRandomFiller fillerThread = null;

	public ConveyorBeltAutomata(AutomataContainer<?, ?, ?> father, ConveyorBeltManager manager) {
		super(father, new ConveyorBeltModel(), new OfflineCommunicationManager());
		this.manager = manager;
		manager.registerObserver(this);
		simulator = new ConveyorBeltSimulator(this.manager);
		Logger.println("AT CB Created");
		fillerThread = new ConveyorBeltRandomFiller(manager);
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

	public static void main(String[] args) {
		ConveyorBeltManager m = new ConveyorBeltManager();
		m.configure(10, 2);
		ConveyorBeltAutomata atcb = new ConveyorBeltAutomata(null, m);
		atcb.startCommand();
		Message mess = new Message("algo", null, false, CommunicationMessageType.CONFIGURATION, null);
		mess.addAttribute(new Attribute(ConveyorBeltManager.CAPACITY, "32"));
		mess.addAttribute(new Attribute(ConveyorBeltManager.SPEED, "8"));
		atcb.injectMessage(mess);
		Message mess2 = new Message("algo", null, false, CommunicationMessageType.START, null);
		atcb.injectMessage(mess2);
	}

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (this.manager.isSensorInitial()) {
			feedInput(ConveyorBeltInput.loadSensorTrue, false);
		}
		if (this.manager.isSensorFinish()) {
			feedInput(ConveyorBeltInput.unloadSensorTrue, false);
		}
		if (!this.manager.isSensorFinish()) {
			feedInput(ConveyorBeltInput.unloadSensorFalse, false);
		}
		if (this.manager.isSensorUnloadMax()) {
			feedInput(ConveyorBeltInput.unloadSensorTrueMax, false);
		}
		if (this.manager.isEmpty()) {
			feedInput(ConveyorBeltInput.empty, false);
		}
	}

	@Override
	protected void consume(Message message) {
		//TODO
	}

	@Override
	public void startCommand() {
		manager.setRunning(true);
		simulator.start();
		fillerThread.start();
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

}
