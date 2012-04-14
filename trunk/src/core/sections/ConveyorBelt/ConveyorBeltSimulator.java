package core.sections.ConveyorBelt;

import core.sections.ParallelPort.ParallelPortState;
import core.sections.ParallelPort.Utils.ParallelPortException;

/**
 * 
 * @author GelidElf
 * 
 */
public class ConveyorBeltSimulator extends Thread {

	private int[] contents = null;
	private ConveyorBeltManager manager = null;
	private int capacity = 0;
	private int numberOfElements = 0;


	public ConveyorBeltSimulator(ConveyorBeltManager m) {
		manager = m;
		contents = new int[manager
				.getBitGroupValue(ConveyorBeltManager.CAPACITY)];
		cleanContentsOfBelt();
	}

	/**
	 * 
	 */
	private void move() {
		printContents("before:");
		for (int i = 1; i < contents.length; i++) {
			contents[i - 1] = contents[i];
		}
		contents[contents.length - 1] = 0;
		try {
			manager.setValueByName(ConveyorBeltManager.SENSOR_LOAD,
					contents[contents.length - 1]);
			manager.setValueByName(ConveyorBeltManager.SENSOR_UNLOAD,
					contents[0]);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printContents("after:");
	}

	private void printContents(String text) {
		System.out.print(text);
		for (int i = 0; i < contents.length; i++) {
			System.out.print(" " + contents[i]);
		}
		System.out.println();
	}

	@Override
	public void run() {
		while (true) {
			try {
				updateCapacity();
				if (initialSensorActive()) {
					setElementInInitialPositionInContents();
					increaseElementCountByOne();
				}
				if (beltHasElements()) {
					int velocity = manager
							.getValueByName(ConveyorBeltManager.SPEED);
					if (running() && finalSensorInactive()) {
						actualizoElElementoDeLaUltimaPosicionQueYaNoEsta();
						System.out.println("muevo la cinta!:" + velocity);
						move();
						sleepExecution(velocity);
					} else {
						System.out.println("NO muevo la cinta!");
						sleepExecution(velocity);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void sleepExecution(int velocity) throws InterruptedException {
		if (velocity == 0) {
			System.out.println("Speed set to 0");
		} else {
			sleep(1000 / velocity);
		}
	}

	private void actualizoElElementoDeLaUltimaPosicionQueYaNoEsta()
			throws ParallelPortException {
		if (positionOccupied(0)) {
			manager.setValueByName(ConveyorBeltManager.QUANTITY, manager
					.getValueByName(ConveyorBeltManager.QUANTITY) - 1);
			numberOfElements--;
		}
	}

	private void setElementInInitialPositionInContents()
			throws ParallelPortException {
		contents[contents.length - 1] = manager
				.getValueByName(ConveyorBeltManager.SENSOR_LOAD);
	}

	private boolean beltHasElements() {
		return contents.length > 0;
	}

	private void updateCapacity() throws ParallelPortException {
		int newCapacity = manager.getValueByName(ConveyorBeltManager.CAPACITY);
		if (newCapacity != capacity) {
			capacity = newCapacity;
			int residualCuantity = changeCapacity(newCapacity);
			manager.setValueByName(ConveyorBeltManager.QUANTITY,
					residualCuantity);
		}
	}

	private boolean running() throws ParallelPortException {
		return manager.getValueByName(ConveyorBeltManager.RUNNING) == 1;
	}

	private boolean finalSensorInactive() throws ParallelPortException {
		return manager.getValueByName(ConveyorBeltManager.SENSOR_UNLOAD) == 0;
	}

	private boolean positionOccupied(int possition) {
		return contents[possition] == 1;
	}

	private void increaseElementCountByOne() throws ParallelPortException {
		manager.setValueByName(ConveyorBeltManager.QUANTITY, manager
				.getValueByName(ConveyorBeltManager.QUANTITY) + 1);
		numberOfElements++;
	}

	private boolean initialSensorActive() throws ParallelPortException {
		return manager.getValueByName(ConveyorBeltManager.SENSOR_LOAD) == 1;
	}

	/**
	 * cleans the contents of the belt
	 */
	private void cleanContentsOfBelt() {
		for (int i = 0; i < capacity; i++) {
			contents[i] = 0;
		}
	}

	private int changeCapacity(int capacity) {
		int quantity = 0;
		if (capacity > contents.length) {
			int[] aux = new int[capacity];
			for (int i = 0; i < contents.length; i++) {
				aux[i] = contents[i];
				if (contents[i] == 1) {
					quantity++;
				}
			}
			contents = aux;
		} else if (capacity < contents.length) {
			int[] aux = new int[capacity];
			for (int i = 0; i < capacity; i++) {
				aux[i] = contents[i];
				if (contents[i] == 1) {
					quantity++;
				}
			}
			contents = aux;
		}
		return quantity;
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
			manager.setValueByName(ConveyorBeltManager.CAPACITY, 10);
			manager.setValueByName(ConveyorBeltManager.SPEED, 2);
		} catch (ParallelPortException e) {
			e.printStackTrace();
		}

		ConveyorBeltSimulator cbs = new ConveyorBeltSimulator(manager);
		cbs.start();
		System.out.println("hilos lanzados1");
		// ATConveyorBelt atcb = new ATConveyorBelt(null, manager);
		// atcb.start();
		// System.out.println("hilos lanzados2");
	}
	
}
