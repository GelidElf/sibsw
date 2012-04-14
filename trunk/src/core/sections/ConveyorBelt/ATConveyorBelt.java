package core.sections.ConveyorBelt;

import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton1MessageFactory;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATConveyorBelt extends AutomataContainer implements
		ParallelPortManagerObserver {

	private ConveyorBeltManager manager = null;
	private AutomataStateCB currentState = null;
	private ConveyorBeltSimulator cbs;
	
	private ConveyorBeltRandomFiller fillerThread = null;

	public ATConveyorBelt(AutomataContainer father, ConveyorBeltManager manager) {
		super(father);
		this.manager = manager;
		manager.registerObserver(this);
		if (currentState == null){
			currentState = AutomataStateCB.stadoInicial(manager.getBitGroupValue(ConveyorBeltManager.CAPACITY));
		}
		manager.setRunning(true);
		cbs = new ConveyorBeltSimulator(this.manager);
		cbs.start();
		System.out.println("AT CB Created");
		fillerThread = new ConveyorBeltRandomFiller(manager);
		fillerThread.start();
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

	public void setupParametersFromMessage(Message mes) {
		String speed = mes
				.getAttributeValue(SlaveAutomaton1MessageFactory.SPEED);
		String capacity = mes
				.getAttributeValue(SlaveAutomaton1MessageFactory.CAPACITY);
		try {
			manager.setValueByName(ConveyorBeltManager.SPEED,
					Integer.parseInt(speed));
			manager.setValueByName(ConveyorBeltManager.CAPACITY,
					Integer.parseInt(capacity));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConveyorBeltManager m = new ConveyorBeltManager();
		m.configure(10, 2);
		ATConveyorBelt atcb = new ATConveyorBelt(null, m);
	}

	@Override
	public void update(ParallelPortManager manager) {
		updateStates(null);
		System.out.println("State:"	+ currentState.getClass().getCanonicalName());
	}
	
	public void updateStates(Message message){
		AutomataStateCB oldState = currentState;
		if (manager.isSensorInitial()){
			currentState = currentState.loadSensorTrue();
		}
		if (manager.isSensorFinish()){
			currentState = currentState.unloadSensorTrue();
		}
		if (!manager.isSensorFinish()){
			currentState = currentState.unloadSensorFalse();
		}
		if (manager.isSensorUnloadMax()){
			currentState = currentState.unloadSensorTrueMax();
		}
		if (manager.isEmpty()){
			currentState = currentState.empty();
		}
		
		if (currentState != oldState){
			//aoisd
		}
	}

}