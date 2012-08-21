package core.sections.ConveyorBelt;

import core.messages.Attribute;
import core.messages.Message;
import core.messages.OfflineCommunicationManager;
import core.messages.enums.CommunicationMessageType;
import core.model.AutomataContainer;
import core.model.DummyAutomataModel;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATConveyorBelt extends AutomataContainer<ATConveyorBeltInput,DummyAutomataModel> implements
		ParallelPortManagerObserver {
	
	private ConveyorBeltManager manager = null;
	private AutomataStateCB currentState = null;
	private ConveyorBeltSimulator simulator;
	
	private ConveyorBeltRandomFiller fillerThread = null;

	public ATConveyorBelt(AutomataContainer<?,?> father, ConveyorBeltManager manager) {
		super(father,new DummyAutomataModel(), new OfflineCommunicationManager());
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
		atcb.startCommand();
		Message mess = new Message("algo", null, false, CommunicationMessageType.CONFIGURATION, null);
		mess.addAttribute(new Attribute(ConveyorBeltManager.CAPACITY,"32"));
		mess.addAttribute(new Attribute(ConveyorBeltManager.SPEED,"8"));
		atcb.injectMessage(mess);
		Message mess2 = new Message("algo", null, false, CommunicationMessageType.START, null);
		atcb.injectMessage(mess2);
	}
	
	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if (this.manager.isSensorInitial()){
			feedInput(ATConveyorBeltInput.loadSensorTrue,false);
		}
		if (this.manager.isSensorFinish()){
			feedInput(ATConveyorBeltInput.unloadSensorTrue,false);
		}
		if (!this.manager.isSensorFinish()){
			feedInput(ATConveyorBeltInput.unloadSensorFalse,false);
		}
		if (this.manager.isSensorUnloadMax()){
			feedInput(ATConveyorBeltInput.unloadSensorTrueMax,false);
		}
		if (this.manager.isEmpty()){
			feedInput(ATConveyorBeltInput.empty,false);
		}
	}

	@Override
	protected void consume(Message message) {
		switch ((ATConveyorBeltInput)message.getInputType()) {
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
	public void startCommand() {
		manager.setRunning(true);
		simulator.start();
		fillerThread.start();
	}

	@Override
	protected void changeConfigurationParameter(Attribute attribute) {
		try {
			manager.setValueByName(attribute.getName(), Integer.parseInt((String)attribute.getValue()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}
		
	}

}