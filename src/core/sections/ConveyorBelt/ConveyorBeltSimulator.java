package core.sections.ConveyorBelt;

import java.util.Random;

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
	private double tiempoEsperaEntrePiezas = 0;
	private double espacioDePieza = 0;
	private int realSpeed = 0;
	private int realCapacity = 0;
	private int realLength = 0;
	private int quantity = 0;

	private Random rand;
	private boolean autoFeed = false;

	public ConveyorBeltSimulator(ConveyorBeltManager manager) {
		this.setName("ConveyorBeltSimulatorThread");
		this.manager = manager;
		contents = new int[this.manager.getBitGroupValue(ConveyorBeltManager.CAPACITY)];
		cleanContentsOfBelt();
		this.manager.registerObserver(this);
		rand = new Random(System.currentTimeMillis());
	}

	/**
	 * 
	 */
	private void move() {
		//		printContents("before:");
		for (int i = 1; i < contents.length; i++) {
			contents[i - 1] = contents[i];
		}
		contents[contents.length - 1] = 0;
		try {
			if (contents[contents.length - 2] == 0){
				manager.setValueByName(ConveyorBeltManager.SENSOR_LOAD, contents[contents.length - 1]);
			}
			manager.setValueByName(ConveyorBeltManager.SENSOR_UNLOAD, contents[0]);
		} catch (ParallelPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		printContents("after:");
	}

	private void printContents(String text) {
		StringBuilder builder = new StringBuilder();
		builder.append(text);
		for (int i = 0; i < contents.length; i++) {
			builder.append(" " + contents[i]);
		}
		Logger.println(builder.toString());
	}


	@Override
	public void run() {
		while (true) {
			try {
				//int velocity = manager.getValueByName(ConveyorBeltManager.SPEED);
				if (running()) {
					setInitialSensorIfRandomFillerActive();
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
					sleepExecution();
				}else{
					sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ParallelPortException e) {
				e.printStackTrace();
			}
		}
	}

	private void setInitialSensorIfRandomFillerActive() throws ParallelPortException {
		if (autoFeed){
			manager.setValueByNameAsBoolean(ConveyorBeltManager.SENSOR_LOAD,rand.nextBoolean());
		}
	}

	private void sleepExecution() throws InterruptedException {
		sleep(new Double(tiempoEsperaEntrePiezas * 1000).longValue());
	}

	private void actualizoElElementoDeLaUltimaPosicionQueYaNoEsta() throws ParallelPortException {
		if (positionOccupied(0)) {
			quantity = quantity - 1;
			//manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY) - 1);
			numberOfElements--;
		}
	}

	private void setElementInInitialPositionInContents() throws ParallelPortException {
		contents[contents.length - 1] = manager.getValueByName(ConveyorBeltManager.SENSOR_LOAD);
	}

	private boolean beltHasElements() {
		return contents.length > 0;
	}

	private void updateCapacity(int newCapacity) throws ParallelPortException {
		if (newCapacity != capacity) {
			capacity = newCapacity;
			int residualCuantity = changeCapacity(newCapacity);
			//manager.setValueByName(ConveyorBeltManager.QUANTITY, residualCuantity);
			quantity = residualCuantity;
		}
	}

	private boolean running() throws ParallelPortException {
		return manager.getValueByNameAsBoolean(ConveyorBeltManager.RUNNING);
	}

	private boolean finalSensorInactive() throws ParallelPortException {
		return !manager.getValueByNameAsBoolean(ConveyorBeltManager.SENSOR_UNLOAD);
	}

	private boolean positionOccupied(int possition) {
		return contents[possition] == 1;
	}

	private void increaseElementCountByOne() throws ParallelPortException {
		//manager.setValueByName(ConveyorBeltManager.QUANTITY, manager.getValueByName(ConveyorBeltManager.QUANTITY) + 1);
		quantity = quantity + 1;
		numberOfElements++;
	}

	private boolean initialSensorActive() throws ParallelPortException {
		return manager.getValueByNameAsBoolean(ConveyorBeltManager.SENSOR_LOAD);
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
		if( (this.manager.getModifiedGroupName().equals(ConveyorBeltManager.SPEED)) ||
				(this.manager.getModifiedGroupName().equals(ConveyorBeltManager.CAPACITY)) ||
				(this.manager.getModifiedGroupName().equals(ConveyorBeltManager.LENGTH)) ){
			int valorPinVelocidad = manager.getBitGroupValue(ConveyorBeltManager.SPEED);
			realSpeed = (valorPinVelocidad * 5) + 20;
			int valorPinCapacidad = manager.getBitGroupValue(ConveyorBeltManager.CAPACITY);
			realCapacity = valorPinCapacidad + 50;
			try {
				updateCapacity(realCapacity);
			} catch (ParallelPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int valorPinLongitud = manager.getBitGroupValue(ConveyorBeltManager.LENGTH);
			realLength = valorPinLongitud + 10;
			espacioDePieza = realLength / realCapacity;
			tiempoEsperaEntrePiezas = espacioDePieza / realSpeed / 60;
		}
	}

	public void setAutoFeeed(boolean enable){
		autoFeed = enable;
	}

	public boolean getAutoFeed(){
		return autoFeed;
	}

}
