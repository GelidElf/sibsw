package core.sections.ConveyorBelt;

import java.util.Random;

import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton1MessageFactory;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ConveyorBelt.States.Idle;
import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATConveyorBelt extends Thread implements ParallelPortManagerObserver	 {

	private ConveyorBeltManager manager = null;
	private AutomataStateCB currentState = null;
	private Random rand = new Random(System.currentTimeMillis());
	private CommunicationManager commManager = null;
	private ConveyorBeltSimulator cbs;
	private AutomataContainer father = null;

	public ATConveyorBelt(AutomataContainer father, ConveyorBeltManager manager) {
		this.manager = manager;
		manager.registerObserver(this);
		this.father = father;
		cbs = new ConveyorBeltSimulator(this.manager);
		cbs.start();
		System.out.println("AT CB Created");
	}

	@Override
	public void run() {

		// Message mes = commManager.getInboxByName("unknown").getMessage();
		//setupParametersFromMessage();
		setupMockParametersFromMessage();
		while (true) {
			if (currentState == null) {
				currentState = Idle.getInstance();
				currentState.action(this);
				startSimulator();
			}
			System.out.println("State:"+currentState.getClass().getCanonicalName());
			try {
				if (isReady()) {
					// quitar esto
					// System.out.println("Estoy listo!");
					/*
					 * try { //sleep(2000);
					 * //_manager.setBitGroupValue(ConveyorBeltManager
					 * .SENSOR_FINISH, 0); } catch (InterruptedException e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); }
					 */

				}
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			currentState = currentState.estop();
		}
	}

	private void startSimulator() {
		try {
			manager.setBitGroupValue(ConveyorBeltManager.RUNNING, 1);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setupParametersFromMessage(Message mes) {
		String speed = mes
				.getAttributeValue(SlaveAutomaton1MessageFactory.SPEED);
		String capacity = mes
				.getAttributeValue(SlaveAutomaton1MessageFactory.CAPACITY);
		try {
			manager.setValueByName(ConveyorBeltManager.SPEED, Integer
					.parseInt(speed));
			manager.setValueByName(ConveyorBeltManager.CAPACITY, Integer
					.parseInt(capacity));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void setupMockParametersFromMessage() {
		int speed = 2;
		int capacity = 10;
		try {
			manager.setValueByName(ConveyorBeltManager.SPEED, speed);
			manager.setValueByName(ConveyorBeltManager.CAPACITY, capacity);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParallelPortException e1) {
			// TODO Auto-generated catch block
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
	public boolean isFirstPositionEmpty(){
		return manager.isSensorInitial();
	}

	public ConveyorBeltManager getManager() {
		return manager;
	}

	public static void main(String[] args) {
		ConveyorBeltManager manager = new ConveyorBeltManager();
		ParallelPortState state = new ParallelPortState();
		manager.setState(state);
		try {
			manager.setValueByName(ConveyorBeltManager.RUNNING, 1);
			manager.setValueByName(ConveyorBeltManager.SENSOR_INITIAL, 1);
			manager.setValueByName(ConveyorBeltManager.CAPACITY, 10);
			manager.setValueByName(ConveyorBeltManager.SPEED, 2);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator(manager);
		cbs.start();
		System.out.println("hilos lanzados1");
		// ATConveyorBelt atcb = new ATConveyorBelt(null, manager);
		// atcb.start();
		// System.out.println("hilos lanzados2");
	}

	@Override
	public void update(ParallelPortManager manager) {
		// TODO Auto-generated method stub
		
	}

}