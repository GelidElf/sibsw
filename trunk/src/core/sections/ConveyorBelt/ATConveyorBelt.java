package core.sections.ConveyorBelt;

import core.messages.Attribute;
import core.messages.Message;
import core.messages.enums.CommunicationIds;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATConveyorBelt extends AutomataContainer<ATConveyorBeltInput> implements
		ParallelPortManagerObserver {
	
	private ConveyorBeltManager manager = null;
	private AutomataStateCB currentState = null;
	private ConveyorBeltSimulator simulator;
	
	private ConveyorBeltRandomFiller fillerThread = null;

	public ATConveyorBelt(AutomataContainer<?> father, ConveyorBeltManager manager) {
		super(father);
		this.manager = manager;	
		manager.registerObserver(this);
		if (currentState == null){
			currentState = AutomataStateCB.estadoInicial(manager.getBitGroupValue(ConveyorBeltManager.CAPACITY));
		}
		simulator = new ConveyorBeltSimulator(this.manager);
		System.out.println("AT CB Created");
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
		ATConveyorBelt atcb = new ATConveyorBelt(null, m);
		Message mess = new Message("algo", null, false, CommunicationMessageType.CONFIGURATION, null);
		mess.addAttribute(new Attribute(ConveyorBeltManager.CAPACITY,"32"));
		mess.addAttribute(new Attribute(ConveyorBeltManager.SPEED,"8"));
		atcb.injectMessage(mess);
		Message mess2 = new Message("algo", null, false, CommunicationMessageType.START, null);
		atcb.injectMessage(mess2);
	}
	
	@Override
	public void update(ParallelPortManager manager) {
		if (this.manager.isSensorInitial()){
			inputStorage.add(ATConveyorBeltInput.loadSensorTrue);
		}
		if (this.manager.isSensorFinish()){
			inputStorage.add(ATConveyorBeltInput.unloadSensorTrue);
		}
		if (!this.manager.isSensorFinish()){
			inputStorage.add(ATConveyorBeltInput.unloadSensorFalse);
		}
		if (this.manager.isSensorUnloadMax()){
			inputStorage.add(ATConveyorBeltInput.unloadSensorTrueMax);
		}
		if (this.manager.isEmpty()){
			inputStorage.add(ATConveyorBeltInput.empty);
		}
	}

	@Override
	protected void consume(ATConveyorBeltInput currentInput) {
		switch (currentInput) {
			case empty:
					currentState.empty();
				break;
			case loadSensorTrue:
				currentState.loadSensorTrue();
			break;
			case estop:
				currentState.estop();
			break;
			case nstop:
				currentState.nstop();
			break;
			case restart:
				currentState.restart();
			break;
			case unloadSensorFalse:
				currentState.unloadSensorFalse();
			break;
			case unloadSensorTrue:
				currentState.unloadSensorTrue();
			break;
			case unloadSensorTrueMax:
				currentState.unloadSensorTrueMax();
			break;
		}		
	}

	@Override
	protected void begin() {
		manager.setRunning(true);
		simulator.start();
		fillerThread.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			manager.setValueByName(attribute.getName(), Integer.parseInt(attribute.getValue()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		
	}

}