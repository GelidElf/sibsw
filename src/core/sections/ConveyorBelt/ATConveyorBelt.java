package core.sections.ConveyorBelt;

import java.util.Random;

import core.messages.CommunicationManager;
import core.messages.Message;
import core.messages.MessageFactory.SlaveAutomaton1MessageFactory;
import core.model.AutomataContainer;
import core.sections.ConveyorBelt.States.AutomataStateCB;
import core.sections.ConveyorBelt.States.Idle;
import core.sections.ParallelPort.ParallelPortObserver;
import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;

public class ATConveyorBelt extends Thread implements ParallelPortObserver {

	private ConveyorBeltManager manager = null;
	private AutomataStateCB currentState = null;
	private Random rand = new Random(System.currentTimeMillis());
	private CommunicationManager commManager = null;
	private ConveyorBeltSimulator cbs;
	private AutomataContainer father = null;

	public ATConveyorBelt(AutomataContainer father, ConveyorBeltManager manager) {
		this.manager = manager;
		this.father = father;
	}

	@Override
	public void update(ParallelPortState state) {
		manager.setState(state);
	}

	@Override
	public void run() {

		// Message mes = commManager.getInboxByName("unknown").getMessage();
		String speed = "5";// mes.getAttributeValue(SlaveAutomaton1MessageFactory.SPEED);
		String capacity = "5";// mes.getAttributeValue(SlaveAutomaton1MessageFactory.CAPACITY);
		try {
			System.out.println("Pongo valores...");
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
		cbs = new ConveyorBeltSimulator(manager);
		cbs.setCapacity(5);
		cbs.start();
		System.out.println("despues de run");
		while (true) {
			if (currentState == null) {
				currentState = Idle.getInstance();
				try {
					manager.setBitGroupValue(ConveyorBeltManager.RUNNING, 1);
				} catch (ParallelPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if (manager.getValueByName(ConveyorBeltManager.SENSOR_INITIAL) == 0) {
					if (rand.nextBoolean()) {
						manager.setValueByName(
								ConveyorBeltManager.SENSOR_INITIAL, 1);
						// System.out.println("sensor = 1");
					} else {
						try {
							/*
							 * NOS TENEMOS QUE DORMIR EL MISMO TIEMPO QUE EL
							 * SIMULADOR, si no, en cuanto el simulador se
							 * duerma, el random hace 1000000 intentos y acaba
							 * por salir true. Esto nos lleva a que SIEMPRE
							 * soltamos un gear/axis, o lo que es lo mismo, la
							 * cinta siempre está llena
							 */
							sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
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

	public void piecePicked() {
		try {
			manager.setBitGroupValue(ConveyorBeltManager.SENSOR_FINISH, 0);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method to check if the piece is ready to pick up
	public boolean isReady() {
		boolean op = false;
		try {
			if (manager.getValueByName(ConveyorBeltManager.SENSOR_FINISH) == 1) {
				op = true;
			}
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return op;
	}

	// Method to check if the first position of the CB is empty
	public boolean isFirstPositionEmpty() throws ParallelPortException {
		boolean empty = false;
		if (manager.getValueByName(ConveyorBeltManager.SENSOR_INITIAL) == 0) {
			empty = true;
		} else {
			empty = false;
		}
		return empty;
	}

	public ConveyorBeltManager getManager() {
		return manager;
	}

	public static void initializeAndConfigure() {

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
	public void setParallelPortState(ParallelPortState state) {
		manager.setState(state);
	}

}