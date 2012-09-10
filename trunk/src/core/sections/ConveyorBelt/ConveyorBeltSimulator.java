package core.sections.ConveyorBelt;

import core.sections.ParallelPort.ParallelPortManager;
import core.sections.ParallelPort.ParallelPortManagerObserver;
import core.sections.ParallelPort.Utils.ParallelPortException;
import core.utilities.log.Logger;

/**
 * 
 * @author GelidElf
 * 
 */
public class ConveyorBeltSimulator extends Thread implements ParallelPortManagerObserver {

	private int[] contents = null;
	private ConveyorBeltManager manager = null;
	private int capacity = 0;
	private int numberOfElements = 0;
	public double velocidadReal = 0;
	public double espacioDePieza = 0;
	int realSpeed = 0;
	int realCapacity = 0;
	int realLength = 0;
	
	public ConveyorBeltSimulator(ConveyorBeltManager manager) {
		this.setName("ConveyorBeltSimulatorThread");
		this.manager = manager;
		contents = new int[this.manager.getBitGroupValue(ConveyorBeltManager.CAPACITY)];
		cleanContentsOfBelt();
	}

	/**
	 * 
	 */
	private void move() {
		//printContents("before:");
		for (int i = 1; i < contents.length; i++) {
			contents[i - 1] = contents[i];
		}
		contents[contents.length - 1] = 0;
		try {
			manager.setValueByName(ConveyorBeltManager.SENSOR_LOAD, contents[contents.length - 1]);
			manager.setValueByName(ConveyorBeltManager.SENSOR_UNLOAD, contents[0]);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//printContents("after:");
	}

	private void printContents(String text) {
		StringBuilder builder = new StringBuilder();
		builder.append(text);
		for (int i = 0; i < contents.length; i++) {
			builder.append(" " + contents[i]);
		}
		Logger.println(builder.toString());
	}

	
	/*
	 *  el run tiene que hacer
	 * como minumo
	 * calcular el tiempo que tiene que dormir el hilo en cada ejkecuccion
	 * usando para ello la longitud, la cantidad y la velocidad
	 */
	@Override
	public void run() {
		while (true) {
			try {
				int velocity = manager.getValueByName(ConveyorBeltManager.SPEED);
				updateCapacity();
				if (running()) {
					if (initialSensorActive()) {
						setElementInInitialPositionInContents();
						increaseElementCountByOne();
					}
					if (beltHasElements()) {
						if (finalSensorInactive()) {
							actualizoElElementoDeLaUltimaPosicionQueYaNoEsta();
							//Logger.println("muevo la cinta!:" + velocity);
							move();
						} else {
							Logger.println("Paramos la cinta!");
							manager.setRunning(false);
						}
					}
				}
				sleepExecution(velocity);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}
	}

	private void sleepExecution(int velocity) throws InterruptedException {
		if (velocity == 0) {
			Logger.println("Speed set to 0");
		} else {
			sleep(1000 / velocity);
		}
	}

	private void actualizoElElementoDeLaUltimaPosicionQueYaNoEsta() throws ParallelPortException {
		if (positionOccupied(0)) {
			manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY) - 1);
			numberOfElements--;
		}
	}

	private void setElementInInitialPositionInContents() throws ParallelPortException {
		contents[contents.length - 1] = manager.getValueByName(ConveyorBeltManager.SENSOR_LOAD);
	}

	private boolean beltHasElements() {
		return contents.length > 0;
	}

	private void updateCapacity() throws ParallelPortException {
		int newCapacity = manager.getValueByName(ConveyorBeltManager.CAPACITY);
		if (newCapacity != capacity) {
			capacity = newCapacity;
			int residualCuantity = changeCapacity(newCapacity);
			manager.setValueByName(ConveyorBeltManager.QUANTITY, residualCuantity);
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
		manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY) + 1);
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

	@Override
	public void updateFromPortManager(ParallelPortManager manager) {
		if( (manager.getModifiedGroupName().equals(ConveyorBeltManager.SPEED)) || 
				(manager.getModifiedGroupName().equals(ConveyorBeltManager.CAPACITY)) || 
				(manager.getModifiedGroupName().equals(ConveyorBeltManager.LENGTH)) ){
			int valorPinVelocidad = manager.getBitGroupValue(ConveyorBeltManager.SPEED);
			realSpeed = (valorPinVelocidad * 5) + 20;
			int valorPinCapacidad = manager.getBitGroupValue(ConveyorBeltManager.CAPACITY);
			realCapacity = valorPinCapacidad + 50;
			int valorPinLongitud = manager.getBitGroupValue(ConveyorBeltManager.LENGTH);
			realLength = valorPinLongitud + 10;
			espacioDePieza = realLength / realCapacity;
		}	

	}


}
